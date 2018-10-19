
function printChart() {

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8888/plotly/",
        data: {codewars_username: $("#codewars_username").val(), from: $("#from").val(), to: $("#to").val()},
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function(data) {
            // console.log("successful ")
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
        }
    });
}

$(document).ready(function () {
    console.log("page ready");
    printChart();
});