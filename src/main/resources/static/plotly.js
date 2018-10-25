
const plotlyUrl = "https://codewars-api-connector-2.herokuapp.com/plotly"

function printChart() {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: plotlyUrl,
        data: {codewars_username: $("#codewars_username").val(), from: $("#from").val(), to: $("#to").val()},
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function(data) {

            var plotlyStats = [
                {
                    x: Object.keys(data).sort().map(dateStr => dateStr.replace("-", ". ")+"."),
                    y: Object.values(data),
                    type: 'bar'
                }
            ];

            Plotly.newPlot('plotly', plotlyStats, {
                title: `Number of katas per month of user ${$("#codewars_username").val()}`,
                yaxis: {
                    tickformat:'d'
                }
            });
        },
        error : function (error) {
            console.log("error getting data for plot " + error);
            printChart();
        }
    });
}

$(document).ready(function () {
    console.log("page ready");
    printChart();
});