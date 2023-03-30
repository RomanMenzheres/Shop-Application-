$(document).ready(function () {

    $('.subtotal-price').each(function () {
        totalProductsPriceCalculate($(this).text().substring(1), true);
    })

    $(".minus").on("click", function (event) {
        event.preventDefault();
        let productId = $(this).attr("pid");
        let productPrice = $(this).attr("price");
        let quantityInput = $("#quantity" + productId);

        let newQuantity = parseInt(quantityInput.val()) - 1;
        if (newQuantity > 0){
            quantityInput.val(newQuantity);
            updateQuantity(productId, newQuantity);
            updateSubAndTotalPrice(productId, newQuantity, productPrice, false);
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
            updateSubAndTotalPrice(productId, newQuantity, productPrice, true);
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

function updateSubAndTotalPrice(productId, quantity, price, plus){
    let newSubTotalPrice = (price * quantity).toFixed(2);
    $("#subtotalPrice" + productId).text("₴" + newSubTotalPrice);
    totalProductsPriceCalculate(price, plus)
}

function totalProductsPriceCalculate(price, plus){

    let link = $('.total-products-price-value');

    let total = link.text().substring(1);

    if (total !== ""){
        if (plus) {
            total = parseFloat(total);
            price = parseFloat(price);

            total = (total + price).toFixed(2);
            link.text("₴" + total);
        } else {
            total = parseFloat(total);
            price = parseFloat(price);

            total = (total - price).toFixed(2);
            link.text("₴" + total);
        }
    } else {
        total = total + price;
        link.text("₴" + total);
    }

    totalCalculate(total);
    deliveryPriceCheck(total);
}

function totalCalculate(totalPrice){
    totalPrice = parseFloat(totalPrice) + parseFloat($(".delivery-price-value").text().substring(1));

    $(".total-price-value").text("₴" + (totalPrice).toFixed(2));
    $(".form-total-price").val(totalPrice);
}