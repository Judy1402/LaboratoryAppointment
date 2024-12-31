<template>
    <section>
        <h2>我 的 课 表</h2><br>
        <form id="searchForm">
            <div>
                <label>周 次：</label>
                <select v-model="weekNum" class="week-select">
                    <option v-for="(item, index) in weeks" :key="index" :value="item">{{item}}</option>
                </select>
            </div>

        </form>
        <br>
        <CourseTable :classroom="fakeClassroom", :week="weekNum" :showDialog=false></CourseTable>
    </section>
</template>

<style scoped>
    section{
        padding: 20px;
        border-radius: 10px;
        background-color: white;
    }
    section h2{
        color: rgb(79, 112, 219);
    }
    #searchForm{
        border: 2px solid rgb(79, 112, 219);
        padding: 10px;
        border-radius: 10px;
        display: flex;
        justify-content: left;
        flex-wrap: wrap;
    }
    #searchForm select{
        border: 1.5px solid rgb(79, 112, 219);
        border-radius: 10px;
        padding: 10px;
        max-width: 120px;
    }
    #searchForm label{
        font-size: 14px;
        font-family: "宋体";
        font-weight: bold;
    }
    #searchForm div{
        line-height: 40px;
        border-left: 5px solid rgb(79, 112, 219);
        padding: 10px;
        margin: 10px;
    }
    .search{
        flex:1;
    }
    .search input{
        border: 1.5px solid rgb(79, 112, 219);
        outline: none;
        border-radius: 10px;
        padding: 10px;
        width: 100%;
    }
</style>

<script setup>

    let temp = localStorage.getItem("user");
    if(temp === null)
    {
        window.location.href="./home";
    }
    import { ref } from 'vue';
    import CourseTable from './TableDetail/CourseTable.vue';
    const weeks = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];
    const weekNum = ref(1);
    import request from '@/axios/request';
    let courseList = null;
    let token  = localStorage.getItem("token");
    request.get("teacher/get/course",{headers:{'token': token}}).then(res =>{
        console.log(res);
        courseList = res.data;
    })
    const fakeClassroom=ref({
        id:"0",
        name:"0"
    });
</script>