$(function () {
    // Hide Header on on scroll down
    var didScroll;
    var lastScrollTop = 0;
    var startScrollTop = 151;
    var delta = 5;
    var navbarHeight;

    $(window).scroll(function (event) {
        didScroll = true;
        navbarHeight = $('.navbar-fixed-animate').outerHeight();
    });

    setInterval(function () {
        if (didScroll) {
            hasScrolled();
            didScroll = false;
        }
    }, 250);

    function hasScrolled() {
        var st = $(this).scrollTop();
        // Make sure they scroll more than delta
        if (Math.abs(lastScrollTop - st) <= delta)
            return;

        // If they scrolled down and are past the navbar, add class .nav-up.
        // This is necessary so you never see what is "behind" the navbar.
        if (st > lastScrollTop && st > navbarHeight) {
            // Scroll Down
            $('.navbar-fixed-animate').removeClass('nav-down').addClass('nav-up');
        } else {
            // Scroll Up
            if (st + $(window).height() < $(document).height()) {
                $('.navbar-fixed-animate').removeClass('nav-up').addClass('nav-down');
            }
        }

        lastScrollTop = st;
    }
});
