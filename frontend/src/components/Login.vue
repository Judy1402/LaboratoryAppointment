<template>
    <section class="dia">
        <div class=" choose" @click="choose">
            {{ text }}
        </div>
        <section id="signInSection" ref="signIn" style="display: none;">
            <form>
                <h2>注 册</h2>
                <div>
                    <input type="text" placeholder="用户名" v-model="user.name">
                </div>
                <div>
                    <input type="text" placeholder="手机号" v-model="user.phone">
                </div>
                <div>
                    <input type="password" placeholder="请输入密码" v-model="user.password">
                </div>
                <div>
                    <input type="password" placeholder="请再次输入密码" v-model="tempPassword">
                </div>
                <div>
                    <button class="login" @click.prevent="signInAccount">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</button>
                </div>
                <div style="display: flex;">
                    <button type="reset">重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置</button>
                    <button @click.prevent="closeLogin">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
                </div>
            </form>
        </section>
        <section id="loginSection" ref="login">
            <form id="accountForm" ref="accountForm">
                <h2>账 号 登 录</h2>
                <br><br><br>
                <div>
                    <input type="text" placeholder="用户账户" v-model="user.account">
                </div>
                <div>
                    <input type="password" placeholder="用户密码" v-model="user.password">
                </div>
                <div>
                    <button class="login" @click.prevent="loginAccount">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
                </div>
                <div style="display: flex;">
                    <button @click.prevent="closeLogin">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
                </div>
            </form>
        </section>
    </section>
</template>

<style scoped>
    .dia{
        border-radius: 10px;
        padding: 10px;
    }
    .choose{
        background: linear-gradient(45deg, rgb(119, 132, 250), rgb(214, 159, 239));
        color: white;
        font-weight: 700;
        padding: 8px;
        border-radius: 0 10px 0 10px;
        border: 1px solid grey;
        position: absolute;
        right: 2px;
        top: 2px;
        cursor: pointer;
        transition: 1s all;
    }
    .choose:hover{
        box-shadow: 0 0 10px white;
    }
    form{
        display: flex;
        flex-wrap: wrap;
    }
    form h2{
        color: rgb(95, 95, 246)
    }
    form div{
        margin: 10px;
        display: block;
        width: 100%;
    }
    form input, button{
        padding: 10px;
        width: 100%;
        display: block;
    }
    #codeForm{
        display: none;
    }
    .getCode{
        border-left: 0;
        width: auto;
        /* border-radius: 0 0 20px 0; */
        border-radius: 0;
    }
    button{
        border: 1px solid grey;
        border-radius: 20px;
        transition: 2s all;
    }
    button:hover{
        box-shadow: 0 0 10px white;
        background: linear-gradient(45deg, lightskyblue, rgb(119, 132, 250));
    }
    .login{
        color: white;
        background-color: rgb(119, 132, 250);
    }
    .trans{
        background-color: lightskyblue;
    }
</style>

<script setup>
    import request from '@/axios/request';
import { ref } from 'vue';

    const text = ref("注 册");
    const signIn = ref(null);
    const login = ref(null);
    const choose = ()=>{
        if(text.value === "注 册"){
            text.value = "登 录";
            login.value.style.display = "none";
            signIn.value.style.display = "block";
        }
        else{
            text.value = "注 册";
            login.value.style.display = "block";
            signIn.value.style.display = "none";
        }
    }

    const accountForm = ref(null);

    const toAccount = () => {
        accountForm.value.style.display = 'flex';
        codeForm.value.style.display = 'none';
    };

    import { inject } from 'vue';
    const closeLogin = inject("closeLogin", false);

    const tempPassword = ref("");
    const user = ref({
        name: "",
        account: "",
        password: "",
        phone: "",
        staus: 1,
        role: "",
    });

    const validPhoe = (phone)=>{
        if(!(/^1[3456789]\d{9}$/.test(phone))){
            alert("手机号码有误，请重填");
            return false;
        }
        return true;
    }

    const signInAccount = ()=>{
        if(user.value.password !== tempPassword.value){
            alert("两次密码不一致");
            return;
        }
        if(!validPhoe(user.value.phone)){
            return;
        }
        user.value.account = user.value.phone;
        request.post("user/signin", user.value).then(res=>{

            if(res.data.code >= 400) alert(res.data.message);
            else{
                localStorage.setItem("user", JSON.stringify(res.data.data));
                localStorage.setItem("token", res.headers['token']);
                alert("注册成功,你的账号是"+res.data.data.account);
                closeLogin();
                // console.log(res);
                window.location.reload();
            }
        })
    
    }
    const loginAccount = ()=>{
        request.post("user/login", user.value).then(res=>{
            // console.log(res.data);
            if(res.data.code >= 400) alert(res.data.message);
            else {
                // console.log(res.data);
                localStorage.setItem("user", JSON.stringify(res.data.data));
                localStorage.setItem("token", res.headers['token']);
                closeLogin();
                window.location.reload();
            }

        })
    }
</script>