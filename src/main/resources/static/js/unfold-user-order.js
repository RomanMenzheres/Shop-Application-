$(document).ready(function () {

    setUpOrdersUnfolding();

});

function setUpOrdersUnfolding() {
    $(".unfold-displayed").on('click', function () {
        let orderId = $(this).attr("oid")

        $("#order" + orderId).css("height", "auto");

        $("#displayed" + orderId).hide();
        $("#hidden" + orderId).show();
    });

    $(".unfold-hidden").on('click', function () {
        let orderId = $(this).attr("oid")

        $("#order" + orderId).css("height", "40px");

        $("#hidden" + orderId).hide();
        $("#displayed" + orderId).show();
    });
}