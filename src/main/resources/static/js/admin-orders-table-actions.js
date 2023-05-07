function action(link, event) {
    event.stopPropagation();

    let orderId = link.attr('oid');
    let action = link.attr('class');

    $.ajax({
        type: 'POST',
        url: '/order/' + action + '/' + orderId,

        success: function (data) {
            let status = data.substring(data.lastIndexOf(' ')  + 1);

            if (status === 'PAID' || status === 'PROCESSING'){
                getOrdersForTable($('.confirmed'), $('.active'))
            } else {
                getOrdersForTable($('.active'), $('.confirmed'))
            }
        },

        error: function () {
            alert('Oops, something went wrong');
        }
    });
}