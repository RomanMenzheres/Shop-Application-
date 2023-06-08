$(document).ready(function () {
    $(".cart-item-delete-button").on("click", function (event) {
        event.preventDefault();
        let cartItemId = $(this).attr("cartId");
        let productId = $(this).attr("pid");

        totalProductsPriceCalculate($('#subtotalPrice' + productId).text().substring(1), false);

        $(this).parents(".cart-item-container").remove();

        isThereAnything($(".list-cart-items"));
        deleteFromCart(cartItemId);
    })
});

function deleteFromCart(cartItemId){
    let url = "/cart/delete/" + cartItemId;

    $.ajax({
        type: "DELETE",
        url: url
    });
}

function isThereAnything(link) {
    if(link.children().length === 0) {
        $(".clear-cart-button").hide();
        $(".total").hide();
        $(".empty-cart").show();
        resetWarningText();
    }
}