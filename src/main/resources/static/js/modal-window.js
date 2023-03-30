$(document).ready(function () {

    const modalWindow = $(".modal");

    modalWindow.css({"display": "flex", "visibility": "hidden", "opacity": "0", "transition": "opacity 300ms easy-in-out"});

    $(".address-input").on("click", function () {
        modalWindow.css({"visibility": "visible", "opacity": "1"})
    });

    modalWindow.on("click", function (event) {
         if (event.target === modalWindow[0] || event.target.closest(".modal-close")) {
             modalWindow.css({"visibility": "hidden", "opacity": "0"})

             setTimeout(() => {}, 300)
         }
    });

});