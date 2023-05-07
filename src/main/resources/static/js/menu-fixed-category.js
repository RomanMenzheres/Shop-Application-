let stickySidebar = $('.manipulating-products').offset().top;

$(window).scroll(function() {
    if ($(window).scrollTop() > stickySidebar - 85) {
        $('.manipulating-products').addClass('affix');
    }
    else {
        $('.manipulating-products').removeClass('affix');
    }
});