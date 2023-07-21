let sidebar = $('.manipulating-products');
let stickySidebar = sidebar.offset().top;

$(window).load(function () {
    if (stickySidebar === 90){
        sidebar.removeClass('affix');
        stickySidebar = sidebar.offset().top;
    }
});

$(window).scroll(function() {
    if ($(window).scrollTop() > stickySidebar - 85) {
        sidebar.addClass('affix');
    }
    else {
        sidebar.removeClass('affix');
    }
});