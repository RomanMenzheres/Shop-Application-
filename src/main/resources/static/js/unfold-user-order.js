$(document).ready(function () {

    $("#unfold-displayed").on('click', function () {
        $(".order").css("height", "auto");

        $("#unfold-displayed").hide();
        $("#unfold-hidden").show();
    });

    $("#unfold-hidden").on('click', function () {
        $(".order").css("height", "30px");

        $("#unfold-hidden").hide();
        $("#unfold-displayed").show();
    });

});