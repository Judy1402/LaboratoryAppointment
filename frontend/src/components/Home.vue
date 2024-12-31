<template>
    <div class="box">
        <div class="inner-box">
            <h1>实 验 室 预 约 系 统</h1>
            <p>————Laboratory Reservation System————</p>
            <button class="toLoginButton" @click="openLogin">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
        </div>
    </div>
    <dialog ref="dialog">
        <Login></Login>
    </dialog>
</template>

<script setup>
    import Login from './Login.vue';
    import { ref } from 'vue';
    const dialog = ref(null);
    const openLogin = () => {
        dialog.value.style.display = 'block';
        dialog.value.showModal();
    };

    import { provide } from 'vue';
    const closeLogin = ()=>{
        dialog.value.style.display = 'none';
        dialog.value.close();
    }
    provide("closeLogin", closeLogin);
</script>

<style scoped>
    .box{
        width: 100%;
        height: calc(100vh - 20px);
        background-image: url("../assets/picture/home.jpg");
        background-repeat: no-repeat;
        background-size: cover;
    }
    .inner-box{
        padding: 100px 0 0 0;

        width: 100%;
        height: 100%;
        backdrop-filter: blur(5px);
        text-align: center;
    }
    .inner-box h1{
        -webkit-text-stroke: 1px white;
        -webkit-text-fill-color: transparent;
        font-size: 80px;
        color:white;
        text-shadow: 0 0 20px;
        overflow: hidden;
        border-right: 3px solid white;
        width: 0;
        height: 1.5em;
        display: inline-block;
        /* animation: typing 2s steps(20, end) 1, typing 2s steps(20, end) infinite 5s; */
        animation: typing 1s steps(20, end) forwards;
    }
    .inner-box h1:hover{
        text-shadow: 0 0 30px;
    }
    @keyframes typing{
        from{width: 0}
        to{width: 70%}
    }
    .inner-box p{
        font-size: 20px;
        color:aliceblue
    }
    .toLoginButton{
        margin-top: 100px;
        background-color: transparent;
        padding: 10px 30px;
        border: 3px solid rgb(119, 132, 250);
        color: rgb(119, 132, 250);
        border-radius: 15px;
        font-weight: bold;
    }
    .toLoginButton:hover{
        background-color: rgb(119, 132, 250);
        -webkit-text-fill-color: white;
        box-shadow: 0 0 10px lightskyblue;
    }
    dialog {
        width: 30%;
        border-radius: 10px;
        margin: auto;
        background: linear-gradient(45deg, rgba(255,255,255, 0.5), rgba(119, 132, 250, 0.5));
        backdrop-filter: blur(5px);
    }
</style>