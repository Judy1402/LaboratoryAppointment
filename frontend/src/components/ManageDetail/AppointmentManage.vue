<template>
    <section class="chooseSection">
        <div>
            <label>周次：</label>
            <select v-model="weekNum" @change="chooseWeek">
                <option v-for="(item, index) in weeks" :key="index" :value="item">{{ item }}</option>
            </select>
        </div>
        <div>
            <label>教室：</label>
            <select v-model="classroom.name" @change="changeRoom">
                <option v-for="(item, index) in Rooms" :key="index" :value="item.name" >{{ item.name }}</option>
            </select>
        </div>
    </section>
    <CourseTable :classroom="classroom" :week="weekNum" :showDialog=true></CourseTable>
</template>
<style scoped>
    .chooseSection{
        display: flex;
        justify-content: space-between;
    }
    select{
        outline: none;
        border: 0;
    }
    .chooseRoom{
        padding: 5px 10px;
        border-radius: 10px;
        border: 2px solid rgb(79, 112, 219);
        outline: none;
    }
</style>

<script setup>
import request from '@/axios/request';
import CourseTable from '../TableDetail/CourseTable.vue';
import { provide, ref } from 'vue';
    const weeks = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];

    const classroom = ref({});

    const token = localStorage.getItem("token");
    const Rooms = ref([]);
    request.get("user/get/classrooms", {headers:{'token': token}}).then(res=>{
        Rooms.value = res.data.data;
        classroom.value=Rooms.value[0];
        // console.log("rooms",Rooms.value);
    });
    const changeRoom = ()=>{
        classroom.value = Rooms.value.find((item) => item.name === classroom.value.name);
    }
    const weekNum = ref(1);
</script>