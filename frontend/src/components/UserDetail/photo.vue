<template>
    <section>
        <div class="photo">
            <img src="" alt="头像" ref="picture">
            <div class="load">
                <input type="file" id="file" accept=".jpg, .png" @change="uploadPhoto">
            </div>
        </div>
        <div class="info">
            <br>
            <h2>{{ user.name }}</h2>
            <span id="profession">{{ user.profession }}</span>
            <span id="description">{{ user.description }}</span>
        </div>
        <div>
            <button  class="edit" v-show="!login" @click="toLogin">登 录</button>
            <button v-show="login" class="out" @click="toLogout">退 出</button>
            <button class="edit" @click="openDialog">编 辑 个 人 信 息</button>
        </div>
        <dialog ref="dialog">
            <h2>编辑个人信息</h2>
            <form>
                <div>
                    <label>姓 名：</label>
                    <input type="text" v-model="user.name" placeholder="姓名">
                </div>
                <div>
                    <label>专 业：</label>
                    <select v-model="user.profession">
                        <option value="软件工程">软件工程</option>
                        <option value="计算机科学技术">计算机科学与技术</option>
                        <option value="数据科学与大数据技术">数据科学与大数据技术</option>
                        <option value="人工智能">人工智能</option>
                    </select>
                </div>
                <div>
                    <label>简 介：</label>
                    <input type="text" v-model="user.description" placeholder="个人简介">
                </div>
                <div>
                    <label>电 话：</label>
                    <input type="text" v-model="user.phone" placeholder="电话">
                </div>
                <div class="buttons">
                    <button type="reset">重置</button>
                    <button @click.prevent="saveInfo">保存</button>
                    <button @click.prevent="closeDialog">关闭</button>
                </div>
            </form>
        </dialog>
    </section>
</template>

<style scoped>
    section{
        padding: 20px;
        border-radius: 10px;
        background-color: white;
        display: flex;
    }
    .photo{
        position: relative;
    }
    .photo img{
        width: 100px;
        height: 100px;
        border-radius: 50%;
        border: 2px solid grey;
       
        display: block;
    }
    .load{
        width: 100px;
        height: 100px;
        border-radius: 50%;
        top:0;
        position: absolute;
        border: 2px solid grey;
        display: none;
        background: url(../../assets/svg/相机.svg) no-repeat;
        background-size: 70px;
        background-position: center;
        cursor: pointer;
        background-color: rgba(0, 0, 0, 0.7);
    }
    img[alt = "头像"]:hover + .load{
        display: block;
    }
    .load:hover{
        display: block;
    }
    #file{
        z-index: 1000;
        border: 3px solid red;
        display: block;
        height: 100px;
        width: 100px;
        opacity: 0;
    }
    .info{
        margin:0 20px;
        flex: 2;
    }
    #profession{
        margin: 5px 0;
        display: inline-block;
        padding: 2px;;
        color: white;
        text-align: center;
        border-radius: 5px;
        background-color: rgb(79, 165, 219);
    }
    #description{
        overflow: hidden;
        display: block;
        background-color: rgba(0, 0, 0, 0.1);
   }
    .edit{
        display: block;
        margin-bottom: 5px;
        width: 130px;
        height: 40px; ;
        padding: 10px;
        border-radius: 10px;
        border: rgb(79, 112, 219) 2px solid;
        color: rgb(79, 112, 219);
        font-weight: 700;
        background-color: white;
        cursor: pointer;
        transition: all 1s;
    }
    .out{
        display: block;
        margin-bottom: 5px;
        width: 130px;
        padding: 10px;
        height: 40px;
        border-radius: 10px;
        border: red 2px solid;
        background-color: red;;
        font-weight: 700;
        color: white;
        transition: 1s all;
    }
    .out:hover{
        background-color: white;
        color: red;
        box-shadow: 0 0 10px red;
    }
    .edit:hover{
        background-color: rgb(79, 112, 219);
        color: white;
    }
    dialog{
        display: none;
        margin: auto;
        padding: 20px;
        border-radius: 10px;
    }
    dialog h2{
        color: rgb(79, 112, 219);;
    }
    dialog div{
        display: flex;
        padding: 10px;
    }
    dialog label{
        display: block;
        height: 38px;
        line-height: 38px;
        text-align: right;
    }
    dialog input, dialog select{
        width: 200px;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid rgb(79, 112, 219);
    }
    dialog input:focus, dialog select:focus{
        outline: none;
        border: 2px solid rgb(79, 112, 219);
        box-shadow: 0 0 8px lightskyblue;
    }
    dialog .buttons{
        display: flex;
        justify-content: space-between;
    }
    dialog button{
        padding: 10px 20px;
        border-radius: 5px;
        border: 1px solid rgb(79, 112, 219);
        background-color: white;
        color: rgb(79, 112, 219);
        cursor: pointer;
    }
    dialog button:hover{
        background-color: rgb(79, 112, 219);
        color: white;
        box-shadow: 0 0 8px lightskyblue;
    }

</style>

<script setup>
    import request from '@/axios/request';
    import { onMounted, onUpdated, ref } from 'vue';

    let temp = localStorage.getItem("user");
    if(temp === null){
        window.location.href='./home';
    }

    const initUser = ref({
        name: '未登录',
        profession: '专业为空',
        description: '描述为空',
        phone: 'default'
    });

    let user = localStorage.getItem('user') ? ref(JSON.parse(localStorage.getItem('user'))) : initUser;
    // if(user.value.description === null){
    //     console.log("null")
    //     user.value.description = '这是张三的个人简介';
    // }
    // if(user.value.profession === null){
    //     user.value.profession = '软件工程';
    // }
    // console.log(user.value);

    const login = ref(user !== initUser);
    const toLogin = ()=>{
        window.location.href = '/home';
    }
    const toLogout = ()=>{
        localStorage.removeItem("user");
        localStorage.removeItem("token");
        window.location.href = '/home';
    }

    const picture = ref(null);
    const uploadPhoto = (e) => {
        const file = e.target.files[0];
        if (!file) return;

        const reader = new FileReader();
        reader.onload = (e) => {
            console.log(e.target.result);
            picture.value.src = e.target.result;
        };
        reader.readAsDataURL(file);
    };
    const dialog = ref(null);
    const openDialog = () => {
        dialog.value.style.display = 'block';
        dialog.value.showModal();
    };
    const closeDialog = () => {
        dialog.value.style.display = 'none';
        dialog.value.close();
    };
    let token = localStorage.getItem("token");
    console.log(token);
    const saveInfo = ()=>{
        request.patch("user/change/information",user.value,{headers:{'Content-Type': 'application/json','token':token}})
        .then(res =>{
            if(res.data.code===200){
                localStorage.setItem('user', JSON.stringify(user.value));
                alert("保存成功");
                closeDialog();
                window.location.reload();
            }
        })

    }
</script>