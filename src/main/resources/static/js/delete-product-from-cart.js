$(document).ready(function () {
    $(".cart-item-delete-button").on("click", function (event) {
        event.preventDefault();
        let cartItemId = $(this).attr("cartId");

        $(this).parents(".cart-item-container").hide();

        isThereAnything($(this).parents(".list-cart-items"));
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
    console.log(link.children(':visible').length === 0);
    if(link.children(':visible').length === 0) {
        link.children(".empty-cart-dynamic").show();
    }
}