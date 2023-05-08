const modalWindow = $(".modal");

$(document).ready(function () {
    modalWindow.css({"display": "flex", "visibility": "hidden", "opacity": "0", "transition": "opacity 300ms easy-in-out"});
    $('.modal-main').css('width', 'auto')

    close(modalWindow);
});

function getOrder(tableRow){
    $.ajax({
        url: '/order/' + tableRow.attr('oid'),
        type: 'GET',
        success: function (data) {
            openOrder(data)
        },
        error: function (xhr, status, error) {
            console.error(error);
        }
    });

}

function openOrder(order){
    $('.order-header').text('Замовлення  №' + order.id);
    switch (order.status){
        case 'PROCESSING': $('<h3 class="status processing">').text('Статус: ОБРОБЛЯЄТЬСЯ').appendTo($('.current-status').empty());
                            break;

        case 'PAID': $('<h3 class="status paid">').text('Статус: СПЛАЧЕНО').appendTo($('.current-status').empty());
                            break;

        case 'CONFIRMED': $('<h3 class="status confirmed">').text('Статус: ПІДТВЕРДЖЕНО').appendTo($('.current-status').empty());
                            break;

        case 'DELIVERED': $('<h3 class="status delivered">').text('Статус: ДОСТАВЛЕНО').appendTo($('.current-status').empty());
                            break;

        case 'CANCELED': $('<h3 class="status canceled">').text('Статус: СКАСОВАНО').appendTo($('.current-status').empty());
                            break;
    }

    $('<span>').text('Повне ім\'я: ' + order.owner.firstName + ' ' + order.owner.lastName).appendTo($('.customer-full-name').empty());
    $('<span>').text('Email: ' + order.owner.email).appendTo($('.customer-email').empty());

    if (order.owner.phone !== null){
        $('<span>').text('Номер Телефону: ' + order.owner.phone).appendTo($('.customer-phone').empty());
    } else {
        $('<span>').text('Номер Телефону: -').appendTo($('.customer-phone').empty());
    }

    if (order.owner.address !== null){
        $('<span>').text('Адреса: ' + order.owner.address).appendTo($('.customer-address').empty());
    } else {
        $('<span>').text('Адреса: -').appendTo($('.customer-address').empty());
    }

    $('<span>').text('Номер Телефону: ' + order.phone).appendTo($('.order-phone').empty());
    $('<span>').text('Адреса: ' + order.address).appendTo($('.order-address').empty());
    if(order.paymentMethod === 'CARD'){
        $('<span>').text('Спосіб оплати: Картка').appendTo($('.order-payment-method').empty());
    } else {
        $('<span>').text('Спосіб оплати: Готівка').appendTo($('.order-payment-method').empty());
    }
    $('<span>').text('Дата створення: ' + order.creationDate).appendTo($('.order-creation-date').empty());

    if (order.comment !== null && order.comment !== '') {
        $('<span>').text('Коментарій: ' + order.comment).appendTo($('.order-comment').empty());
    } else {
        $('<span>').text('Коментарій: -').appendTo($('.order-comment').empty());
    }

    if (order.cancelDate !== null){
        $('<span>').text('Дата скасування: ' + order.cancelDate).appendTo($('.order-cancel-date').empty());
    }
    if (order.deliveryDate !== null){
        $('<span>').text('Дата Доставки: ' + order.deliveryDate).appendTo($('.order-delivery-date').empty());
    }

    let productsDiv = $('.order-products').empty();

    $('<h4>').text('Продукти: ').appendTo(productsDiv);

    for (let i = 0; i<order.products.length; i++){
        let product = order.products[i];

        let productDiv = $('<div class="product">').appendTo(productsDiv);

        let productDescriptionDiv = $('<div class="product-description">').appendTo(productDiv);

        let productImageDiv = $('<div class="product-image">').appendTo(productDescriptionDiv);
        $('<img>').attr('src', '../product images/' + product.productId.name + '.png').appendTo(productImageDiv);

        let productTitleDiv = $('<div class="product-title">').appendTo(productDescriptionDiv);
        $('<span>').text('' + product.productId.name).appendTo(productTitleDiv);

        let productPriceDiv = $('<div class="product-price">').appendTo(productDiv);

        let priceDiv = $('<div class="price">').appendTo(productPriceDiv);
        $('<span>').text('$' + product.productId.price).appendTo(priceDiv);

        let quantityDiv = $('<div class="quantity">').appendTo(productPriceDiv);
        $('<span>').text('' + product.quantity + ' шт.').appendTo(quantityDiv);

        let subtotalPriceDiv = $('<div class="subtotal-price">').appendTo(productPriceDiv);
        $('<span>').text('$' + parseFloat(product.productId.price * product.quantity).toFixed(2)).appendTo(subtotalPriceDiv);

    }

    $('.fastest').off('click');
    $('.shortest').off('click');
    $('.bicycle').off('click');

    $('.fastest').on('click', function () {
       buildRoute($(this), order.address);
    });

    $('.shortest').on('click', function () {
        buildRoute($(this), order.address);
    });

    $('.bicycle').on('click', function () {
        buildRoute($(this), order.address);
    });

    modalWindow.css({"visibility": "visible", "opacity": "1"})
    buildRoute($('.fastest'), order.address);
}
