/*!
    * Start Bootstrap - SB Admin v6.0.2 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2020 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
(function($) {
    "use strict";

    // Add active state to sidbar nav links
    var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
        $("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
            if (this.href === path) {
                $(this).addClass("active");
            }
        });

    // Toggle the side navigation
    $("#sidebarToggle").on("click", function(e) {
        e.preventDefault();
        $("body").toggleClass("sb-sidenav-toggled");
    });
})(jQuery);

const btn = document.querySelector('.eye');
const password = document.querySelector('.pw');

btn.addEventListener('click', () => {
    if (password.type === 'password') {
        password.type = 'text';
        btn.classList.add('active');
    } else {
        password.type = 'password';
        btn.classList.remove('active');
    }
});

function dropdown() {
    document.querySelector("#side-menu").classList.toggle('hidden')
}

function printDiv(divId) {
    var printContents = document.getElementById(divId).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}

// Get the box icon element
var boxIcon = document.querySelector('box-icon[name="image-add"]');

// Get the file input element
var fileInput = document.getElementById('picture');

// Add event listener to box icon for click event
boxIcon.addEventListener('click', function () {
    // Trigger click event on file input
    fileInput.click();
});


// function show(x) {
//     var x = document.getElementById("myDIV");
//     if (x.style.display === "none") {
//         x.style.display = "block";
//     } else {
//         x.style.display = "none";
//     }
// }

// document.getElementById('expense-button').addEventListener('click', function() {
//     document.getElementById('expense-content').style.display = 'block';
//     document.getElementById('income-content').style.display = 'none';
// });

// document.getElementById('income-button').addEventListener('click', function() {
//     document.getElementById('income-content').style.display = 'block';
//     document.getElementById('expense-content').style.display = 'none';
// });

$(document).ready(function () {
    $("#income-button").click(function () {
        $("#income-content").show();
        $("#expense-content").hide();
        $("#income-button").addClass("button-active");
        $("#expense-button").removeClass("button-active");
    });

    $("#expense-button").click(function () {
        $("#income-content").hide();
        $("#expense-content").show();
        $("#income-button").removeClass("button-active");
        $("#expense-button").addClass("button-active");
    });
});
