$(document).ready(function () {



    $(".unfold-displayed").on('click', function () {
        let orderId = $(this).attr("oid")

        $("#order" + orderId).css("height", "auto");

        $("#displayed" + orderId).hide();
        $("#hidden" + orderId).show();
    });

    $(".unfold-hidden").on('click', function () {
        let orderId = $(this).attr("oid")

        $("#order" + orderId).css("height", "30px");

        $("#hidden" + orderId).hide();
        $("#displayed" + orderId).show();
    });

});