<template>
    <h2>
        <button @click="openAddUser">
            <img src="../../assets/svg/添加.svg">
            添加用户
        </button>
        <!-- <button>
            <img src="../../assets/svg/文件.svg">
            文件导入
        </button> -->
    </h2>
    <dialog ref="addUserDialog">
        <h2>添加用户</h2>
        <div>
            <input type="text" placeholder="用户名" v-model="user.name">
            <input type="text" placeholder="手机号" v-model="user.phone">
            <input type="password" placeholder="密 码" v-model="user.password">
            用户权限：
            <label><input type="radio" value="QAQ<" v-model="user.role">管 理</label>
            <label><input type="radio" checked value="~OVO" v-model="user.role">普 通</label>
        </div>
        <div>
            <button @click.prevent="addUser">添 加</button>
            <button @click.prevent="closeUserDialog">关 闭</button>
        </div>
    </dialog>
    <table>
        <thead>
            <tr>
                <th>序号</th>
                <th>用户名</th>
                <th>角色</th>
                <th>专业</th>
                <th>手机号</th>
                <th>创建日期</th>
                <th>最近日期</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(item,index) in userList" :key="index">
                <td>{{ index+1 }}</td>
                <td>{{ item.name }}</td>
                <td>{{ item.role==="QAQ<"?"管理":"普通" }}</td>
                <td>{{ item.profession }}</td>
                <td>{{ item.phone }}</td>
                <td>{{ item.createTime }}</td>
                <td>{{ item.loginTime }}</td>
                <td>
                    <button @click="openDialog(item)" class="set">设置</button>
                    <button @click="deleteUser(item)" class="delete">删除</button>
                </td>
            </tr>
        </tbody>
    </table>
    <dialog ref="dialog">
        <form>
            <h2>用户设置</h2>
            <div>
                <input type="text" readonly :value="tempUser.name">
            </div>
            <div>
                <input type="password" placeholder="重置密码" v-model="tempUser.password">
            </div>
            <div>
                用户权限：
                <label><input type="radio" name="role" value="QAQ<" v-model="tempUser.role">管理</label>
                <label><input type="radio" name="role" value="~OVO" v-model="tempUser.role" checked>普通</label>
            </div>
            <div>
                <button @click.prevent="setUser">确 定</button>
                <button @click.prevent="closeDialog">关 闭</button>
            </div>
        </form>
    </dialog>
</template>
<style scoped>
    h2{
        display: flex;
        justify-content: right;
    }
    h2 button{
        display: flex;
        border: 2px solid rgb(79, 112, 219);
        border-radius: 10px;
        background-color: white;
        padding: 5px 10px;
        margin-left: 10px;
        line-height: 30px;
        cursor: pointer;
        color: rgb(79, 112, 219)
    }
    h2 button:hover{
        color: white;
        background: linear-gradient(45deg, rgb(119, 132, 250), rgb(214, 159, 239));
    }
    h2 button img{
        display: block;
        height: 30px;
    }
    table{
        width: 100%;
    }
    table th,tr{
        width: 100%;
        display: flex;
        justify-content: space-between;
        border-bottom: 2px solid lightskyblue;
    }
    table tr:nth-child(even){
        background-color: aliceblue;
    }
    table td{
        flex:1;
        padding: 10px;
        display: block;
    }
    table td button{
        cursor: pointer;
        border-radius: 5px;
        padding: 5px 10px;
    }
    .set{
        border:2px solid rgb(79, 112, 219);
        background-color: white;
        color: rgb(119, 132, 250);
    }
    .set:hover{
        background-color: rgb(119, 132, 250);
        color: white;
    }
    .delete{
        border: 2px solid red;
        background-color: red;
        color: white;
    }
    .delete:hover{
        background-color: white;
        color: red;
    }
    dialog{
        text-align: center;
        padding: 20px;
        border-radius: 10px;
        margin: auto;
    }
    dialog input[type="text"],input[type="password"]{
        display: block;
        outline: none;
        border: 2px solid rgb(119, 132, 250);
        border-radius: 10px;
        margin-bottom: 5px;
        padding: 10px;
    }
    dialog input:focus{
        box-shadow: 0 0 10px lightskyblue;
    }
    dialog button{
        background-color: white;
        padding: 5px 10px;
        border-radius: 5px;
        border: 1px solid rgb(119, 132, 250);
        color: rgb(119, 132, 250);
    }
    dialog button:hover{
        background-color: rgb(119, 132, 250);
        color: white;
    }
</style>

<script setup>

    import request from '@/axios/request';
    import { ref } from 'vue';

    
    const token= localStorage.getItem("token");
    const userList = ref(null);
    const getUsers = ()=>{
        request.get("admin/users", {headers:{'token': token}}).then(res =>{
            userList.value = res.data.data;
            console.log(userList.value);
            // console.log(userList.value);
        });
    }
    getUsers();

    const user = ref(
        {
            name:"",
            phone:"",
            account:"",
            password:"",
            role:"~OvO",
            status: 1
        }
    );
    const addUserDialog = ref(null);
    const openAddUser = ()=>{
        addUserDialog.value.showModal();
    }
    const closeUserDialog = ()=>{
        addUserDialog.value.close();
    }
    const addUser = ()=>{
        const phonePattern = /^1[3-9]\d{9}$/;
        if (!phonePattern.test(user.value.phone)) {
            alert("请输入有效的手机号");
            return;
        }
        user.value.account = user.value.phone;
        request.post("admin/add/user", user.value, {headers: {'token': token}}).then(res => {
            console.log(res);
        });
        window.location.reload();
    }

    const tempUser = ref({
        id: "",
        name:"",
        password:"",
        role:""
    });
    const dialog = ref(null);
    const openDialog = (item)=>{
        tempUser.value.id = item.id;
        tempUser.value.name=item.name;
        // tempUser.value.password = item.password;
        tempUser.value.role = item.role;
        dialog.value.showModal();
    }
    const closeDialog = ()=>{
        dialog.value.close();
    }
    const setUser = ()=>{
        // console.log("user is",tempUser);
        request.post("admin/set/user", tempUser.value, {headers:{'token':token}}).then(res=>{
            console.log(res);
        })
        closeDialog();
        window.location.reload();
    }
    const deleteUser =(item)=>{
        request.delete("admin/delete/"+item.account, {headers:{'token': token}}).then(res =>{
            console.log(res);
        });
        window.location.reload();
    }
</script>