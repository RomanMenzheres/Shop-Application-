function deleteProduct(link) {

    $.ajax({
        url: '/product/' + link.attr('pid') + '/delete',
        type: 'GET',
        success: function () {
            location.reload();
        }
    });

}