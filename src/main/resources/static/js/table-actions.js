function action(link) {
    let orderId = link.attr('oid');
    let action = link.attr('class');

    $.ajax({
        type: 'POST',
        url: '/order/' + action + '/' + orderId,

        success: function (data) {
            let status = data.substring(data.lastIndexOf(' ')  + 1);

            console.log(status)

            if (status === 'PAID' || status === 'PROCESSING'){
                changeTable($('.confirmed'), $('.active'))
            } else {
                changeTable($('.active'), $('.confirmed'))
            }
        },

        error: function () {
            alert('Oops, something went wrong');
        }
    });
}