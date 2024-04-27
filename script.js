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