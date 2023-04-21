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
    $('.order-header').text('Order №' + order.id);
    $('<h3>').text('Status: ' + order.status).appendTo($('.status').empty());

    $('<span>').text('Full Name: ' + order.owner.firstName + ' ' + order.owner.lastName).appendTo($('.customer-full-name').empty());
    $('<span>').text('Email: ' + order.owner.email).appendTo($('.customer-email').empty());

    if (order.owner.phone !== null){
        $('<span>').text('Phone: ' + order.owner.phone).appendTo($('.customer-phone').empty());
    } else {
        $('<span>').text('Phone: -').appendTo($('.customer-phone').empty());
    }

    if (order.owner.address !== null){
        $('<span>').text('Address: ' + order.owner.address).appendTo($('.customer-address').empty());
    } else {
        $('<span>').text('Address: -').appendTo($('.customer-address').empty());
    }

    $('<span>').text('Phone: ' + order.phone).appendTo($('.order-phone').empty());
    $('<span>').text('Address: ' + order.address).appendTo($('.order-address').empty());
    $('<span>').text('Payment Method: ' + order.paymentMethod).appendTo($('.order-payment-method').empty());
    $('<span>').text('Creation Date: ' + order.creationDate).appendTo($('.order-creation-date').empty());

    if (order.comment !== null && order.comment !== '') {
        $('<span>').text('Comment: ' + order.comment).appendTo($('.order-comment').empty());
    } else {
        $('<span>').text('Comment: -').appendTo($('.order-comment').empty());
    }

    if (order.cancelDate !== null){
        $('<span>').text('Cancel Date: ' + order.cancelDate).appendTo($('.order-cancel-date').empty());
    }
    if (order.deliveryDate !== null){
        $('<span>').text('Delivery Date: ' + order.deliveryDate).appendTo($('.order-delivery-date').empty());
    }

    let productsDiv = $('.order-products').empty();

    $('<h4>').text('Products: ').appendTo(productsDiv);

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
        $('<span>').text('' + product.quantity + ' thing(s)').appendTo(quantityDiv);

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
