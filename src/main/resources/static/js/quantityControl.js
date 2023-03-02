$(document).ready(function () {

    $(".minus").on("click", function (event) {
        event.preventDefault();
        let productId = $(this).attr("pid");
        let productPrice = $(this).attr("price");
        let quantityInput = $("#quantity" + productId);

        let newQuantity = parseInt(quantityInput.val()) - 1;
        if (newQuantity > 0){
            quantityInput.val(newQuantity);
            updateQuantity(productId, newQuantity);
            updateSubTotalPrice(productId, newQuantity, productPrice);
        }
    });

    $(".plus").on("click", function (event) {
        event.preventDefault();
        event.preventDefault();
        let productId = $(this).attr("pid");
        let productPrice = $(this).attr("price");
        let quantityInput = $("#quantity" + productId);

        let newQuantity = parseInt(quantityInput.val()) + 1;
        if (newQuantity < 100){
            quantityInput.val(newQuantity);
            updateQuantity(productId, newQuantity);
            updateSubTotalPrice(productId, newQuantity, productPrice);
        }
    });

});

function updateQuantity(productId, quantity){
    let url = "/cart/update/" + productId + "/" + quantity;

    $.ajax({
        type: "POST",
        url: url
    });
}

function updateSubTotalPrice(productId, quantity, price){
    $("#subtotalPrice" + productId).text("₴" + (price * quantity).toFixed(2));
}