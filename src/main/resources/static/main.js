const NINJAS_API_URL = 'https://api.api-ninjas.com';
const NINJAS_API_REQUEST_HEADERS = {
    'X-Api-Key': 'C3hBsSanqujcKnjGdRhfaw==PEOdHBhZv8NVzDmT',
    'Content-Type': 'application/json'
};
const pageBg = document.querySelectorAll(".page-bg");
const mModalContent = document.querySelectorAll(".mModalContent");
const modalContentColor = document.querySelectorAll(".modalContentColor");
const mThemeModeSwitcher = document.getElementById("themeModeSwitcher");

let darkModeOn = false;

const createCookie = (name, value, days) => {
    let expires;
    if (days) {
        let date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "expires=" + date.toGMTString();
    } else {
        expires = "";
    }
    document.cookie = name + "=" + value + "; SameSite=Lax;" + expires + "; path=/";
}

const readCookie = name => {
    let nameEQ = name + "=";
    let ca = document.cookie.split(";");
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) === " ") {
            c = c.substring(1, c.length);
        }
        if (c.indexOf(nameEQ) === 0) {
            return c.substring(nameEQ.length, c.length);
        }
    }
    return null;
}

const deleteCookie = name => {
    createCookie(name, "", -1);
}

function setDark() {
    for (i = 0; i < pageBg.length; i++) {
        pageBg[i].style.backgroundColor = '#3C4748';
    }
    for (i = 0; i < mModalContent.length; i++) {
        mModalContent[i].style.backgroundColor = '#3C4748';
        mModalContent[i].style.color = 'white';
    }
    for (i = 0; i < modalContentColor.length; i++) {
        modalContentColor[i].style.color = 'white';
    }
}

function setLight() {
    for (i = 0; i < pageBg.length; i++) {
        pageBg[i].style.backgroundColor = 'white';
    }
    for (i = 0; i < mModalContent.length; i++) {
        mModalContent[i].style.backgroundColor = 'white';
        mModalContent[i].style.color = 'black';
    }
    for (i = 0; i < modalContentColor.length; i++) {
        modalContentColor[i].style.color = 'black';
    }
}

function lightsSwitcher() {
    if (!mThemeModeSwitcher.checked) {
        darkModeOn = true;
        createCookie("my_preferredMode", "dark-mode", 365);
        setDark();

    } else {
        darkModeOn = false;
        createCookie("my_preferredMode", "light-mode", 365);
        setLight();
    }
}

const getPreferredMode = () => {
    if (readCookie("my_preferredMode")) {
        return readCookie("my_preferredMode");
    } else {
        return "not-set";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    if (readCookie("my_preferredMode")) {
        darkModeOn = readCookie("my_preferredMode") === "dark-mode";
    } else {
        if (window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)").matches) {
            darkModeOn = true;
        } else {
            darkModeOn = pageBg.style.backgroundColor === '#3C4748';
        }
    }

    if (darkModeOn) {
        setDark();
        mThemeModeSwitcher.checked = false;
        $('#themeModeSwitcher').bootstrapToggle('off');
    } else {
        setLight();
        mThemeModeSwitcher.checked = true;
        $('#themeModeSwitcher').bootstrapToggle('on');
    }
});


function showVideoModal(model, brand, url) {
    setVideoTitle(model, brand);
    setVideoUrl(url);
    $("#videoModal").modal();
}

function showPictureModal(url) {
    document.getElementById("pictureInModal").src = url;
    $("#pictureModal").modal();
}

function stopVideo() {
    setVideoTitle("");
    var currentIframe = document.getElementById("videoPlayer");
    currentIframe.src = currentIframe.src;
}

function stopVideoCarDetails() {
    var currentIframe = document.getElementById("carDetailsVideoPlayer");
    currentIframe.src = currentIframe.src;
}

function setVideoTitle(model, brand) {
    var modalHeader = document.getElementById("videoTitle");
    modalHeader.innerHTML = brand + " " + model;
}

function setVideoUrl(url) {
    var iframe = document.getElementById("videoPlayer");
    iframe.src = url;
}

function showDeleteModal(id) {
    $('#carDeleteModal #delRef').attr('href', '');
    $.get("/car/?id=" + id, function (car) {
        document.getElementById("carDeleteDesc").innerHTML = "Ar tikrai pašalinti <b>" + car.brand + " " + car.model + "</b> ?";
    });

    $('#carDeleteModal #delRef').attr('href', "/delete/?id=" + id);
    $("#carDeleteModal").modal();
}

$(document).ready(function () {
    $('.eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        document.getElementById('btnEditOrCreate').innerHTML = "Atnaujinti";

        $.get(href, function (car, status) {
            document.getElementById("editModalHeaderTitle").innerHTML = "Redaguoti <b>" + car.brand + " " + car.model + "</b>";
            $('#carCreateModal #id').val(car.id);
            $('#carCreateModal #creatorId').val(car.creatorId);
            $('#carCreateModal #brand').val(car.brand);
            $('#carCreateModal #model').val(car.model);
            $('#carCreateModal #engine').val(car.engine);
            $('#carCreateModal #powerInKw').val(car.powerInKw);
            $('#carCreateModal #engineCapacityInL').val(car.engineCapacityInL);
            $('#carCreateModal #bodyType').val(car.bodyType);
            $('#carCreateModal #year').val(car.year);
            $('#carCreateModal #gearType').val(car.gearType);
            $('#carCreateModal #pictureUrl').val(car.pictureUrl);
            $('#carCreateModal #videoUrl').val(car.videoUrl);
            $('#carCreateModal #description').val(car.description);
        });

        $("#editCreateModalForm").attr("action", "/update");
        $("#carCreateModal").modal();
    });

    $('.detailsBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (car, status) {
            document.getElementById("carDetailsTitle").innerHTML = car.brand + " " + car.model;
            document.getElementById("carDetailsPicture").src = car.pictureUrl;
            document.getElementById("carDetailsVideoPlayer").src = car.videoUrl;
            document.getElementById("carDetailsInfo").innerHTML = car.engineCapacityInL + "L, " + car.engine + ", " + car.year + "m</br>" + car.gearType + ", " + car.powerInKw + "kW, " + car.bodyType;
        });

        $("#carDetailsModal").modal();
    });


    $('.cBtn').on('click', function (event) {
        event.preventDefault();
        document.getElementById("editModalHeaderTitle").innerHTML = "Pridėti automobilį";
        document.getElementById('btnEditOrCreate').innerHTML = "Pridėti";
        $('#carCreateModal #id').val('');
        $('#carCreateModal #creatorId').val('');
        $('#carCreateModal #brand').val('');
        $('#carCreateModal #model').val('');
        $('#carCreateModal #engine').val('');
        $('#carCreateModal #powerInKw').val('');
        $('#carCreateModal #engineCapacityInL').val('');
        $('#carCreateModal #bodyType').val('');
        $('#carCreateModal #year').val('');
        $('#carCreateModal #gearType').val('');
        $('#carCreateModal #pictureUrl').val('');
        $('#carCreateModal #videoUrl').val('');
        $('#carCreateModal #description').val('');

        $("#editCreateModalForm").attr("action", "/save")
        $("#carCreateModal").modal();
    });

    $('.gasBtn').on('click', function (event) {
        document.getElementById('gasBody').innerHTML = "Skaičiuojamos kuro sąnaudos...";
        event.preventDefault();

        var href = $(this).attr('href');

        $.get(href, function (car, status) {
            document.getElementById("gasTitle").innerHTML = "<b>" + car.brand + " " + car.model + "</b> kuro sąnaudos";

            const requestUrl = NINJAS_API_URL + "/v1/cars" + "?make=" + car.brand + "&model=" + car.model.replace(/\s+/g, '') + "&limit=1";

            axios.get(requestUrl, {headers: NINJAS_API_REQUEST_HEADERS})
                .then(response => {
                    if (response.data.length > 0) {
                        const consCity = convertMpgToL(response.data[0]['city_mpg']);
                        const consHighway = convertMpgToL(response.data[0]['highway_mpg']);
                        const consCombined = convertMpgToL(response.data[0]['combination_mpg']);

                        document.getElementById('gasBody').innerHTML = "Mieste: " + consCity + " L/100km" + "</br>"
                            + "Užmiestyje: " + consHighway + " L/100km" + "</br>"
                            + "Mišrus: " + consCombined + " L/100km" + "</br>";
                    } else {
                        document.getElementById('gasBody').innerHTML = "Šios modifikacijos automobilis nebuvo rastas duomenų bazėje :(";
                    }
                })
                .catch(error => {
                    document.getElementById('gasBody').innerHTML = "Nepavyko prisijungti prie duomenų bazės :(";
                });
        });


        $("#gasModal").modal();
    });
});

/* convert miles per gallon into L/100km */
function convertMpgToL(mpgValue) {
    return (235.214583 / mpgValue).toFixed(1);
}
