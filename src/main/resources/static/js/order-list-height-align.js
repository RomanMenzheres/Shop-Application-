$(document).ready(function () {

    $(".order").change(function () {

        let orderBoxes = $(".order");

        orderBoxes.forEach(function (orderBox) {
            let topBox = orderBox.children($(".top"));

            if (topBox.height > orderBox.height){
                orderBox.css("height", topBox.height + 10 + "px");
            }
        });

    });

});