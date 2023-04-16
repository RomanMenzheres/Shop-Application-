function deliveryPriceCheck(totalPrice){
    if (totalPrice < 10){
        $(".minimum-price-warning-block").show();
        $(".warning-text").text("The minimum order amount is $10. Add products worth $"
            + (10 - totalPrice).toFixed(2) + ".");
    } else {
        $(".minimum-price-warning-block").hide();
    }

    let remainder = 30 - totalPrice;

    if(remainder > 0){
        $(".progress-bar-container").show();
        $(".delivery-price-value").text("$8.00")
        $(".delivery-text").text("Add products worth $" + remainder.toFixed(2) +" to get free delivery.");
        let progressBarPercent = (totalPrice / 30);
        let newWidth = parseFloat($(".progress-bar").width()) * progressBarPercent;
        $(".progress-bar-fill").width(newWidth);
    } else {
        $(".delivery-price-value").text("$0.00")
        $(".delivery-text").text("You have got free delivery!");
        $(".progress-bar-container").hide();
    }
}

function resetWarningText() {
    $(".warning-text").text("The minimum order amount is $10.");
}