<template>
    <button class="addCourse" @click="openDialog"><img src="../../assets/svg/添加.svg">添加课程</button>
    <dialog ref="dialog">
        <form>
            <h2>添加课程</h2>
            <div>
                <input type="text" placeholder="课程名" v-model="course.name">
            </div>
            <div>
                <input type="text" placeholder="课程人数" v-model.number="course.quantity">
            </div>
            <div>
                <input type="text" placeholder="年级" v-model="course.year">
            </div>
            <div>
                <br>
                <label>专 业：</label>
                <select v-model="course.profession">
                    <option value="软件工程">软件工程</option>
                    <option value="计算机科学技术">计算机科学与技术</option>
                    <option value="数据科学与大数据技术">数据科学与大数据技术</option>
                    <option value="人工智能">人工智能</option>
                </select>
            </div>
            <div>
                <label>学 期：</label>
                <select v-model.number="course.term">
                    <option value="1">上</option>
                    <option value="2">下</option>
                </select>
            </div>
            <div>
                <label>班 级：</label>
                <select v-model="course.classNumber">
                    <option value="01">01</option>
                    <option value="02">02</option>
                    <option value="03">03</option>
                    <option value="04">04</option>
                    <option value="05">05</option>
                    <option value="06">06</option>
                    <option value="07">07</option>
                    <option value="08">08</option>
                    <option value="09">09</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                </select>
            </div>
            <div>
                <input type="text" placeholder="理论课时" v-model.number="course.theoreticalHours">
            </div>
            <div>
                <input type="text" placeholder="实验课时" v-model.number="course.experimentalHours">
            </div>
            <div>
                <input type="text" placeholder="学分" v-model.number="course.credit">
            </div>
            <div>
                <input type="text" placeholder="节数" v-model.number = "course.lesson">
            </div>
            <br>
            <div style="display: flex; justify-content: space-between;">
                <button @click.prevent="addCourse">添 加</button>
                <button @click.prevent="closeDialog">关 闭</button>
            </div>
        </form>
    </dialog>
    <table>
        <thead>
            <tr>
                <th>序 号</th>
                <th>教 师</th>
                <th>课程名</th>
                <th>人 数</th>
                <th>专 业</th>
                <th>年 级</th>
                <th>学 期</th>
                <th>班 级</th>
                <th>学 分</th>
                <th>理论课时</th>
                <th>实验课时</th>
                <th>节 数</th>
                <th>操 作</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(item, index) in courseList" :key="index">
                <td>{{ index + 1 }}</td>
                <td>{{ item.teacherName }}</td>
                <td>{{ item.name }}</td>
                <td>{{ item.quantity }}</td>
                <td>{{ item.profession }}</td>
                <td>{{ item.year }}</td>
                <td>{{ item.term }}</td>
                <td>{{ item.classNumber }}</td>
                <td>{{ item.credit }}</td>
                <td>{{ item.theoreticalHours }}</td>
                <td>{{ item.experimentalHours }}</td>
                <td>{{ item.lesson }}</td>
                <td>
                    <div>
                        <button class="deleteCourse" @click="deleteCourse(item)">删除</button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>
<style scoped>
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th, td {
        border: 1px solid lightskyblue;
        padding: 10px;
        text-align: center;
    }
    th {
        background-color: #f2f2f2;
    }
    tr:nth-child(even) {
        background-color: aliceblue;
    }
    .deleteCourse:hover {
        background-color: red;
        border: 1px solid grey;
        color: white;
        cursor: pointer;
    }
    .deleteCourse{
        padding: 5px 10px;
        background-color: white;

        border: 2px solid red;
        color: red;
    }
    .updateCourse {
        background-color: white;
        border: 1px solid lightskyblue;
        padding: 5px 10px;
        cursor: pointer;
    }
    .updateCourse:hover{
        background-color: lightskyblue;
    }
    .addCourse{
        margin-left: auto;
        margin-bottom: 10px;
        border-radius: 10px;
        background-color: white;
        display: flex;
        padding: 5px;
        border: 3px solid rgb(79, 112, 219);
        color: rgb(79, 112, 219);
        line-height: 30px;
    }
    .addCourse:hover{
        color:white;
        background: linear-gradient(45deg, rgb(214, 159, 239), rgb(79,112,219));
    }
    .addCourse img{
        display: block;
        height: 30px;
    }
    dialog{
        margin: auto;
        width: 320px;
        padding: 20px;
        border-radius: 10px;
    }
    dialog input,select{
        outline: none;
        width: 100%;
        margin-top: 10px;
        border-radius: 10px;
        padding: 10px;
        border: 2px solid rgb(79, 112, 219);
    }
    dialog input:hover{
        box-shadow: 0 0 10px lightskyblue;
    }
    dialog button{
        border: 1px solid rgb(79, 112, 219);
        border-radius: 5px;
        padding: 5px 20px;
        background-color: white;
        color: rgb(79, 112, 219);
    }
    dialog button:hover{
        background-color: rgb(79, 112, 219);
        color: white;
    }
    select{
        width: calc(100% - 54px);
    }
</style>

<script setup>
import { provide, ref } from 'vue';
import request from '@/axios/request';

let user = JSON.parse(localStorage.getItem("user"));
let courseList = ref([]);
let token = localStorage.getItem("token");

provide("courseList", courseList.value);
const getCourse = () => {
    if(user.role === "QAQ<"){
        request.get("admin/get/courseDTO", { headers: { 'token': token } }).then(res => {
            courseList.value = res.data.data;
            console.log(courseList.value);
        });
    }
    else{
        request.get("teacher/get/coursDTO", { headers: { 'token': token } }).then(res => {
            courseList.value = res.data.data;
            console.log(courseList.value);
        });
    }
};

getCourse();

const deleteCourse = (item)=>{
    if(user.role === "QAQ<"){
        request.delete("admin/delete/course/"+item.id, {headers:{'token':token}}).then(res =>{
            window.location.reload();
            console.log(res.data);
        })
    }
    else{
        request.delete("teacher/delete/course/"+item.id, {headers:{'token':token}}).then(res =>{
            window.location.reload();
            console.log(res.data);
        })
    }
}
const dialog = ref(null);
const openDialog = ()=>{
    console.log("open");
    dialog.value.showModal();
}
const closeDialog = ()=>{
    dialog.value.close();
}

const course = ref({
    name: "",
    quantity: 50,
    year:"",
    profession: "",
    term: 1,
    classNumber:"",
    theoreticalHours:null,
    experimentalHours:null,
    credit: null,
    lesson: null
});
const addCourse = ()=>{
    request.post("teacher/add/course", course.value, {headers:{'token': token}}).then(res=>{
        closeDialog();
        if(res.data.code>=400)
        {
            console.log(res);
            alert("添加失败");
            return;
        }
        alert("添加成功");
        window.location.reload();
    })
}

</script>
