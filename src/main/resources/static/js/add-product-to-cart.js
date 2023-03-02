$(document).ready(function () {
    $(".add-product-to-cart-image").on("click", function (event) {
        event.preventDefault();
        let productId = $(this).attr("pid");
        addToCart(productId);
    })
});

function addToCart(productId){
    let url = "/cart/add/" + productId;

    $.ajax({
        type: "POST",
        url: url
    });
}