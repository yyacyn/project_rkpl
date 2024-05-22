// Assuming you have fetched these data from your server
var days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
var quantities = [10, 15, 7, 12, 9, 14, 11]; // Quantities sold each day

var ctx = document.getElementById("myAreaChart");
var myLineChart = new Chart(ctx, {
  type: 'bar',
  data: {
    labels: days,
    datasets: [{
      label: "Quantity Sold",
      backgroundColor: "rgba(2,117,216,0.2)",
      borderColor: "rgba(2,117,216,1)",
      data: quantities,
    }],
  },
  options: {
    scales: {
      xAxes: [{
        time: {
          unit: 'day'
        },
        gridLines: {
          display: false
        },
        ticks: {
          maxTicksLimit: 7
        }
      }],
      yAxes: [{
        ticks: {
          min: 0,
          max: Math.max(...quantities) + 10, // Adjust this as needed
          maxTicksLimit: 5
        },
        gridLines: {
          color: "rgba(0, 0, 0, .125)",
        }
      }],
    },
    legend: {
      display: false
    }
  }
});