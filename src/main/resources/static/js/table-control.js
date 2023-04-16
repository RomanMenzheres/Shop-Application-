$(document).ready(function () {

    let isClicked;
    changeTable($('.confirm'), $('.active'))

    if (isClicked === undefined) {
        isClicked = $('.active');
        $('.active').css({'background': '#84BF41', 'color': 'white'});
    }

    $('.active').on('click', function () {
        changeTable(isClicked, $(this));
        isClicked = $(this);
    });

    $('.confirmed').on('click', function () {
        changeTable(isClicked, $(this));
        isClicked = $(this);
    });

    $('.finished').on('click', function () {
        changeTable(isClicked, $(this));
        isClicked = $(this);
    });

});

function changeTable(isClicked, link) {
    if (isClicked.attr('class') !== link.attr('class')) {
        $.ajax({
            url: '/order/' + link.attr('class'),
            type: 'GET',
            success: function (data) {
                if (link.attr('class') === 'finished') {
                    tableForFinishedOrders(data);
                } else {
                    tableForNotFinishedOrders(isClicked.attr('class'), data);
                }
            },
            error: function (xhr, status, error) {
                console.error(error);
            }
        });

        link.css({"color": "white", "background": "#84BF41"});
        isClicked.attr("style", "");
    }
}

function tableForFinishedOrders(data) {
    headForTable(false);

    let ordersTableBody = $('#table tbody');

    ordersTableBody.empty();

    if (data.length === 0){
        let row = $('<tr class="table-row">').appendTo(ordersTableBody);
        $('<td class="table-data">').text('Table has no info!').appendTo(row);
    }

    $.each(data, function (index, order) {
        let row = $('<tr onclick="getOrder($(this))" class="table-row">').appendTo(ordersTableBody);

        row.attr('oid', order.id);
        if (index !== data.length - 1){
            row.css('border-bottom', '1px solid #e0e0e0')
        }

        $('<td class="table-data-id">').text(order.id).appendTo(row);
        $('<td class="table-data-price">').text('$' + order.price).appendTo(row);
        $('<td class="table-data">').text(order.address).appendTo(row);
        $('<td class="table-data">').text(order.phone).appendTo(row);
        $('<td class="table-data">').text(order.status).appendTo(row);
        $('<td class="table-data">').text(order.creationDate).appendTo(row);
        if (order.status === 'CANCELED') {
            $('<td class="table-data">').text(order.cancelDate).appendTo(row);
        } else {
            $('<td class="table-data">').text(order.deliveryDate).appendTo(row);
        }
    });
}

function tableForNotFinishedOrders(isHeadWasChanged, data) {
    if (isHeadWasChanged === 'finished') {
        headForTable(true);
    }

    let ordersTable = $('#table tbody');

    ordersTable.empty();

    if (data.length === 0){
        let row = $('<tr class="table-row">').appendTo(ordersTable);
        $('<td class="table-data">').text('Table has no info!').appendTo(row);
    }

    $.each(data, function (index, order) {
        let row = $('<tr onclick="getOrder($(this))" class="table-row">').appendTo(ordersTable);

        row.attr('oid', order.id);
        if (index !== data.length - 1){
            row.css('border-bottom', '1px solid #e0e0e0')
        }

        $('<td class="table-data-id">').text(order.id).appendTo(row);
        $('<td class="table-data-price">').text('$' + order.price).appendTo(row);
        $('<td class="table-data">').text(order.address).appendTo(row);
        $('<td class="table-data">').text(order.phone).appendTo(row);
        $('<td class="table-data">').text(order.status).appendTo(row);
        $('<td class="table-data">').text(order.creationDate).appendTo(row);

        let confirmButton = $('<button onclick="action($(this))" class="confirm">').attr('oid', order.id);
        let cancelButton = $('<button onclick="action($(this))" class="cancel">').attr('oid', order.id);
        let actionDiv = $('<div class="actions">');


        $('<img class="displayed" src="../images/admin/green-check-mark.png">').appendTo(confirmButton);
        $('<img class="hidden" src="../images/admin/white-check-mark.png">').appendTo(confirmButton);

        $('<img class="displayed" src="../images/admin/red-cross.png">').appendTo(cancelButton);
        $('<img class="hidden" src="../images/admin/white-cross.png">').appendTo(cancelButton);

        confirmButton.appendTo(actionDiv);
        cancelButton.appendTo(actionDiv);

        $('<td class="table-data">').html(actionDiv).appendTo(row);
    });
}

function headForTable(isNeedDefault) {

    let ordersTableHeader = $('#table thead');

    ordersTableHeader.empty();

    let headRow = $('<tr>').appendTo(ordersTableHeader);

    headRow.attr('class', 'table-head-row')

    $('<td>').text('Id').appendTo(headRow).attr('class', 'header-item-id');
    $('<td>').text('Price').appendTo(headRow).attr('class', 'header-item-price');
    $('<td>').text('Address').appendTo(headRow).attr('class', 'header-item');
    $('<td>').text('Phone').appendTo(headRow).attr('class', 'header-item');
    $('<td>').text('Status').appendTo(headRow).attr('class', 'header-item');
    $('<td>').text('Creation Date').appendTo(headRow).attr('class', 'header-item');

    if (isNeedDefault) {
        $('<td>').text('Actions').appendTo(headRow).attr('class', 'header-item');
    } else {
        $('<td>').text('Delivery/Cancel Date').appendTo(headRow).attr('class', 'header-item');
    }
}