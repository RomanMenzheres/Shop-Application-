function close(link) {
    link.on("click", function (event) {
        if (event.target === link[0] || event.target.closest(".modal-close")) {
            link.css({"visibility": "hidden", "opacity": "0"})

            setTimeout(() => {
            }, 300)
        }
    });
}