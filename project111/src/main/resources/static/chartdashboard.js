const ctx = document.getElementById('lineChart');

new Chart(ctx, {
  type: 'line',
  data: {
    labels: ['Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu', 'Minggu'],
    datasets: [{
      label: 'Total Penjualan',
      data: [1, 4, 3, 5, 2, 3, 8],
      borderWidth: 1
    }]
  },
  options: {
    scales: {
      y: {
        beginAtZero: true
      }
    }
  }
});