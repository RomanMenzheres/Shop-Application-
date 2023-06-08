$(document).ready(function () {
    $(".clear-cart-button").on("click", function () {
        deleteAllFromCart();
    })
});

function deleteAllFromCart(){
    let url = "/cart/delete/all";

    $.ajax({
        type: "DELETE",
        url: url,
        success: function () {
            $( "#shopping-cart" ).load( location.href + " #shopping-cart",  function () {});
        }
    });
}