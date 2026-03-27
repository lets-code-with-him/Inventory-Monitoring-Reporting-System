document.addEventListener("DOMContentLoaded", function () {

    const barCanvas = document.getElementById('barChart');
    const pieCanvas = document.getElementById('pieChart');

    /* ---------- BAR CHART  ---------- */

    const ctx = barCanvas.getContext('2d');

    const gradient = ctx.createLinearGradient(0,0,0,400);
    gradient.addColorStop(0,'#8b5cf6');
    gradient.addColorStop(1,'#06b6d4');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: productNames,
            datasets: [{
                label: 'Stock Quantity',
                data: productQuantities,
                backgroundColor: gradient,
                borderRadius: 8,
                borderSkipped:false
            }]
        },
        options:{
            responsive:true,
            plugins:{
                legend:{
                    display:false
                },
                tooltip:{
                    backgroundColor:"#111827",
                    titleColor:"#fff",
                    bodyColor:"#fff"
                }
            },
            scales:{
                x:{
                    ticks:{color:"#e2e8f0"},
                    grid:{display:false}
                },
                y:{
                    ticks:{color:"#e2e8f0"},
                    grid:{color:"rgba(255,255,255,0.05)"}
                }
            }
        }
    });

    /* ---------- PIE CHART  ---------- */
    new Chart(pieCanvas, {
        type: 'doughnut',
        data: {
            labels: ['Low Stock','Medium Stock','High Stock'],
            datasets: [{
                data: [lowStock,mediumStock,highStock],
                backgroundColor:[
                    '#ef4444',
                    '#f59e0b',
                    '#10b981'
                ],
                borderWidth:0,
                hoverOffset:15
            }]
        },
        options:{
            cutout:'65%',
            plugins:{
                legend:{
                    position:'bottom',
                    labels:{
                        color:"#e2e8f0",
                        padding:20
                    }
                }
            }
        }
    });

});