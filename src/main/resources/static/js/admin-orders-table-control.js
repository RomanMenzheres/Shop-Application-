$(document).ready(function () {

    getOrdersForTable($('.confirm'), $('.active'))

    let isClicked = $('.active');
    $('.active').css({'background': '#84BF41', 'color': 'white'});

    $('.active').on('click', function () {
        if (isClicked.attr('class') === 'confirmed-order-map') {
            afterMap();
        }

        getOrdersForTable(isClicked, $(this));
        isClicked = $(this);
        $('.table').attr('currentOrders', $(this).attr('class'));
    });

    $('.confirmed').on('click', function () {
        if (isClicked.attr('class') === 'confirmed-order-map') {
            afterMap();
        }

        getOrdersForTable(isClicked, $(this));
        isClicked = $(this);
        $('.table').attr('currentOrders', $(this).attr('class'));
    });

    $('.delivering').on('click', function () {
        if (isClicked.attr('class') === 'confirmed-order-map') {
            afterMap();
        }

        getOrdersForTable(isClicked, $(this));
        isClicked = $(this);
        $('.table').attr('currentOrders', $(this).attr('class'));
    });

    $('.finished').on('click', function () {
        if (isClicked.attr('class') === 'confirmed-order-map') {
            afterMap();
        }

        getOrdersForTable(isClicked, $(this));
        isClicked = $(this);
        $('.table').attr('currentOrders', $(this).attr('class'));
    });

    $('.confirmed-order-map').on('click', function () {
        isClicked.attr("style", "");
        isClicked = $(this);
        $(this).css({"color": "white", "background": "#84BF41"});
        $('.main').css('display', 'block');
        $('.orders').css('display', 'none');
        getConfirmedOrdersForMap();
    });

});

function afterMap() {
    $('.main').css('display', 'none');
    $('.orders').css('display', 'block');
}

function getOrdersForTable(isClicked, link) {
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

    if (data.length === 0) {
        let row = $('<tr class="table-row">').appendTo(ordersTableBody);
        $('<td class="table-data">').text('Нажаль, таблиця пуста :(').appendTo(row);
    }

    $.each(data, function (index, order) {
        let row = $('<tr onclick="getOrder($(this))" class="table-row">').appendTo(ordersTableBody);

        row.attr('oid', order.id);
        if (index === data.length - 1) {
            row.addClass('table-last-row')
        }

        $('<td class="table-data-id">').text(order.id).appendTo(row);
        $('<td class="table-data-price">').text('$' + order.price).appendTo(row);
        $('<td class="table-data">').text(order.address).appendTo(row);
        $('<td class="table-data">').text(order.phone).appendTo(row);
        $('<td class="table-data">').text(order.creationDate).appendTo(row);
        if (order.status === 'CANCELED') {
            $('<td class="table-data status canceled">').text('СКАСОВАНО').appendTo(row);
            $('<td class="table-data">').text(order.cancelDate).appendTo(row);
        } else {
            $('<td class="table-data status delivered">').text('ДОСТАВЛЕНО').appendTo(row);
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

    if (data.length === 0) {
        let row = $('<tr class="table-row">').appendTo(ordersTable);
        $('<td class="table-data">').text('Нажаль, таблиця пуста :(').appendTo(row);
    }

    $.each(data, function (index, order) {
        let row = $('<tr onclick="getOrder($(this))" class="table-row">').appendTo(ordersTable);

        row.attr('oid', order.id);
        if (index === data.length - 1) {
            row.addClass('table-last-row')
        }

        $('<td class="table-data-id">').text(order.id).appendTo(row);
        $('<td class="table-data-price">').text('$' + order.price).appendTo(row);
        $('<td class="table-data">').text(order.address).appendTo(row);
        $('<td class="table-data">').text(order.phone).appendTo(row);
        $('<td class="table-data">').text(order.creationDate).appendTo(row);

        if (order.status === 'PROCESSING') {
            $('<td class="table-data status processing">').text('ОБРОБЛЯЄТЬСЯ').appendTo(row);
        } else if (order.status === 'PAID') {
            $('<td class="table-data status paid">').text('СПЛАЧЕНО').appendTo(row);
        } else if (order.status === 'CONFIRMED') {
            $('<td class="table-data status confirmed">').text('ПІДТВЕРДЖЕНО').appendTo(row);
        } else if (order.status === 'UPDATED') {
            $('<td class="table-data status updated">').text('ОНОВЛЕНО').appendTo(row);
        } else {
            $('<td class="table-data status delivering">').text('В ДОРОЗІ').appendTo(row);
        }

        let confirmButton = $('<button onclick="action($(this), event)" class="confirm">').attr('oid', order.id);
        let cancelButton = $('<button onclick="action($(this), event)" class="cancel">').attr('oid', order.id);

        confirmButton.attr('from', 'admin');
        cancelButton.attr('from', 'admin');

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

    $('<td>').text('ID').appendTo(headRow).attr('class', 'header-item-id');
    $('<td>').text('Ціна').appendTo(headRow).attr('class', 'header-item-price');
    $('<td>').text('Адреса').appendTo(headRow).attr('class', 'header-item');
    $('<td>').text('Номер Телефону').appendTo(headRow).attr('class', 'header-item');
    $('<td>').text('Статус').appendTo(headRow).attr('class', 'header-item');
    $('<td>').text('Дата Створення').appendTo(headRow).attr('class', 'header-item');

    if (isNeedDefault) {
        $('<td>').text('Actions').appendTo(headRow).attr('class', 'header-item');
    } else {
        $('<td>').text('Дата Доставки/Скасування').appendTo(headRow).attr('class', 'header-item');
    }
}