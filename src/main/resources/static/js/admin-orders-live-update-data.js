let countOfActiveOrders;
let currentActiveOrders;

$(document).ready(function () {
    setInterval(function () {

        getActiveOrders();
        if (countOfActiveOrders === undefined && currentActiveOrders !== undefined){
            countOfActiveOrders = currentActiveOrders;
        }

        if (currentActiveOrders > countOfActiveOrders){
            countOfActiveOrders = currentActiveOrders
            changeTable($('.confirmed'), $('.active'));
        }

    }, 10000)

});

function getActiveOrders(){
    $.ajax({
        type: 'GET',
        url: '/order/active',

        success: function (data) {
            currentActiveOrders = data.length;
        },

        error: function () {
            alert('Oops, something went wrong!')
        }
    });
}