function deliveryPriceCheck(totalPrice){
    if (totalPrice < 200){
        $(".minimum-price-warning-block").show();
        $(".warning-text").text("The minimum order amount is 200.00 UAH. Add products worth "
            + (200 - totalPrice).toFixed(2) + " UAH.");
    } else {
        $(".minimum-price-warning-block").hide();
    }

    let remainder = 500 - totalPrice;

    if(remainder > 0){
        $(".progress-bar-container").show();
        $(".delivery-text").text("Add products worth " + remainder.toFixed(2) +" UAH to get free delivery.");
        let progressBarPercent = (totalPrice / 500);
        let newWidth = parseFloat($(".progress-bar").width()) * progressBarPercent;
        $(".progress-bar-fill").width(newWidth);
    } else {
        $(".delivery-text").text("You have got free delivery!");
        $(".progress-bar-container").hide();
    }
}

function resetWarningText() {
    $(".warning-text").text("The minimum order amount is 200.00 UAH.");
}