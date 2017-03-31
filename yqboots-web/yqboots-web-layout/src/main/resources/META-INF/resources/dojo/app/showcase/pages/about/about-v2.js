define(['baf/util/ProgressBar', 'jquery/appear', 'jquery/parallax', 'jquery/owl-carousel/owl-carousel',
    'jquery/counter/waypoints', 'jquery/counter/counterup'], function (ProgressBar) {
    return {
        startup: function () {
            $('.counter').counterUp({
                delay: 10,
                time: 1000
            });

            $('.parallaxBg').parallax("50%", 0.2);
            $('.parallaxBg1').parallax("50%", 0.4);

            $(".owl-clients").owlCarousel({
                items: 7,
                autoPlay: 5000,
                itemsDesktop: [1000, 5],
                itemsDesktopSmall: [900, 4],
                itemsTablet: [600, 3],
                itemsMobile: [300, 2]
            });

            ProgressBar.startup('v');
        }
    }
});