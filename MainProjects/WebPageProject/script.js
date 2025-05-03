let list = document.getElementsByClassName("item");
let next = document.getElementById("next");
let prev = document.getElementById("prev");

let count = list.length;
let active = 0;

next.onclick = () => {
let activeOld = document.querySelector('.active');
activeOld.classList.remove('active');

active = active >= count - 1 ? 0 : active = active + 1;
list[active].classList.add('active');
}

prev.onclick = () => {
let activeOld = document.querySelector('.active');
activeOld.classList.remove('active');

active = active === 0 ? 2 : active = active - 1;
list[active].classList.add('active');
}