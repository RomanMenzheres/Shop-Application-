function deliveryPriceCheck(totalPrice){
    if (totalPrice < 10){
        $(".minimum-price-warning-block").show();
        $(".warning-text").text("Мінімальна сума замовлення $10. Додайте продуктів ще на $"
            + (10 - totalPrice).toFixed(2) + ".");
    } else {
        $(".minimum-price-warning-block").hide();
    }

    let remainder = 30 - totalPrice;

    if(remainder > 0){
        $(".progress-bar-container").show();
        $(".delivery-price-value").text("$8.00")
        $(".delivery-text").text("Додайте продуктів ще на $" + remainder.toFixed(2) +", щоб отримати безкоштовну доставку.");
        let progressBarPercent = (totalPrice / 30);
        let newWidth = parseFloat($(".progress-bar").width()) * progressBarPercent;
        $(".progress-bar-fill").width(newWidth);
    } else {
        $(".delivery-price-value").text("$0.00")
        $(".delivery-text").text("Ви отримали безкоштовну доставку!");
        $(".progress-bar-container").hide();
    }
}

function resetWarningText() {
    $(".warning-text").text("Мінімальна сума замовлення $10.");
}