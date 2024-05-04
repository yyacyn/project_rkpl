const btn = document.querySelector('.eye');
const password = document.querySelector('.pw');

btn.addEventListener('click', () => {
    if (password.type === 'password') {
        password.type = 'text';
        btn.classList.add('active');
    } else {
        password.type = 'password';
        btn.classList.remove('active');
    }
});

function dropdown() {
    document.querySelector("#side-menu").classList.toggle('hidden')
}

function printDiv(divId) {
    var printContents = document.getElementById(divId).innerHTML;
    var originalContents = document.body.innerHTML;

    document.body.innerHTML = printContents;

    window.print();

    document.body.innerHTML = originalContents;
}