define(['baf/util/CubePortfolioCreator', 'jquery/cube-portfolio/cube-portfolio'], function (CubePortfolioCreator) {
    return {
        startup: function () {
            CubePortfolioCreator.startup4fw($('#grid-container'), $('#filters-container'));
        }
    }
});