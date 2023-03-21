$(document).ready(function () {
    $(".clear-cart-button").on("click", function () {

        $(this).hide();
        $(".list-cart-items").hide();
        $(".total").hide();
        $(".empty-cart").show();

        deleteAllFromCart();
        resetWarningText();
    })
});

function deleteAllFromCart(){
    let url = "/cart/delete/all";

    $.ajax({
        type: "DELETE",
        url: url
    });
}