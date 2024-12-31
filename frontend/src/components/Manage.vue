<template>
    <section>
        <h1>
            教 室 预 约  
            <img src="../assets/svg/上边.svg" alt="上边" style="display: inline;">
            <img src="../assets/svg/下边.svg" alt="下边" style="display: none;">
        </h1>
        <div id="appointment" style = "display: block;">
            <div>
                <!-- <form>
                    <div>
                        <label>教 室：</label>
                        <input id="room" type="text" list="rooms">
                        <datalist id="rooms">
                            <option>丹青楼901</option>
                            <option>丹青楼902</option>
                            <option>丹青楼903</option>
                            <option>丹青楼904</option>
                            <option>丹青楼905</option>
                            <option>丹青楼906</option>
                            <option>丹青楼907</option>
                            <option>丹青楼908</option>
                            <option>丹青楼909</option>
                            <option>成栋楼910</option>
                        </datalist>
                    </div>
                    <div>
                        <label>起始周次：</label>
                        <select>
                            <option v-for="week in weeks" :key="week">{{week}}</option>
                        </select>
                    </div>
                    <div>
                        <label>结束周次：</label>
                        <select>
                            <option v-for="week in weeks" :key="week">{{week}}</option>
                        </select>
                    </div>
                    <div>
                        <label>起始节次：</label>
                        <select>
                            <option v-for="(item, index) in lessons" :key="index">{{ item }}</option>
                        </select>
                    </div>
                    <div>
                        <label>结束节次：</label>
                        <select>
                            <option v-for="(item, index) in lessons" :key="index">{{ item }}</option>
                        </select>
                    </div>
                    <div>
                        <button>提交</button>
                        <button type="reset">重置</button>
                    </div>
                </form> -->
            </div>
            <br>
            <hr>
            <br>
            <AppointmentManage></AppointmentManage>
        </div>
    </section>
    <section>
        <h1>
            课 程 管 理
            <img src="../assets/svg/上边.svg" alt="上边">
            <img src="../assets/svg/下边.svg" alt="下边">
        </h1>
        <div id="course">
            <CourseManage></CourseManage>
        </div>
    </section>
    <!-- <section>
        <h1>
            教 室 管 理
            <img src="../assets/svg/上边.svg" alt="上边">
            <img src="../assets/svg/下边.svg" alt="下边">
        </h1>
        <div id="classroom">

        </div>
    </section> -->
    <section v-show="show">
        <h1>
            用 户 管 理
            <img src="../assets/svg/上边.svg" alt="上边">
            <img src="../assets/svg/下边.svg" alt="下边">
        </h1>
        <div id="user">
            <UserManage></UserManage>
        </div>
    </section>
</template>

<style scoped>
    section{
        padding: 20px;
        border-radius: 10px;
        background-color: white;
        margin-bottom: 10px;
    }
    section h1{
        color: rgb(79, 112, 219);
    }
    h1 img{
        width: 20px;
        cursor: pointer;
    }
    h1 img[alt="上边"]{
        display: none;
    }
    h1 + div{
        margin-top: 20px;
        padding: 10px;
        display: none;
        border-bottom: 2px solid rgb(79, 112, 219);
        border-top: 2px solid rgb(79, 112, 219);
    }
    form{
        line-height: 40px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
    }
    input{
        padding: 5px;
    }
    select{
        padding: 5px;
    }
    button{
        padding: 10px 20px;
        border: none;
        background-color: white;
        border: 2px solid rgb(79, 112, 219);
        color: rgb(79, 112, 219);
        border-radius: 10px;
        cursor: pointer;
        margin-left: 10px;
    }
    button:hover{
        background-color: rgb(79, 112, 219);
        color: white;
    }
</style>

<script setup>
let temp = localStorage.getItem("user");
    if(temp===null)
    {
        window.location.href = "./home";
    }
    import { ref } from 'vue'
    import { onMounted } from 'vue'
import CourseManage from './ManageDetail/CourseManage.vue';
import UserManage from './ManageDetail/UserManage.vue';
import AppointmentManage from './ManageDetail/AppointmentManage.vue';

    
    onMounted(()=>{
        const imgUp = document.querySelectorAll('h1 img[alt="上边"]');
        const imgDown = document.querySelectorAll('h1 img[alt="下边"]');
        const divs = document.querySelectorAll('h1 + div');
        for(let i = 0; i < imgUp.length; i++){
            imgUp[i].onclick = function(){
                imgUp[i].style.display = 'none';
                imgDown[i].style.display = 'inline';
                divs[i].style.display = 'none';
            }
            imgDown[i].onclick = function(){
                imgUp[i].style.display = 'inline';
                imgDown[i].style.display = 'none';
                divs[i].style.display = 'block';
            }
        }

    })
    const weeks = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
    const lessons = [1,2,3,4,5,6,7,8,9,10,11,12];

    const user = JSON.parse(localStorage.getItem("user"));
    const show = ref(user.role === "QAQ<");
</script>