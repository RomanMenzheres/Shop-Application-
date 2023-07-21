$(document).ready(function () {

    $(".substitution-button").on("click", function () {

        let address = $(this).attr("address");
        let phone = $(this).attr("phone");

        if(address != null) {
            $(".address-input").val(address);
        }

        if (phone != null) {
            $(".phone-input").val(phone);
        }
    });

});