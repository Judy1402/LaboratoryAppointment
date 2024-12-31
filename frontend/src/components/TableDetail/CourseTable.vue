<template>
    <section style="flex:1">
        <table class="course-table">
            <thead>
                <tr>
                    <th>节次</th>
                    <th>周一</th>
                    <th>周二</th>
                    <th>周三</th>
                    <th>周四</th>
                    <th>周五</th>
                    <th>周六</th>
                    <th>周日</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="period in 12" :key="period">
                    <td>{{ period }}</td>
                    <td v-for="day in 7" :key="day" class="course-cell" @click="openDialog(day,period)">
                        <div v-show="classroom && showDialog">
                            <div v-if="(appointment = getAppointment(week, period, day, props.classroom.name)) ">
                                <div @click.stop="openDetail(week, period, day, classroom.name)" :class="{'highlightR': appointment.teacherId === uid}">
                                    {{ appointment.courseName }}
                                    <hr>
                                    {{ appointment.teacherName }}
                                    <br>
                                    {{ appointment.classroomName }}
                                </div>
                                
                            </div>
                        </div>
                        
                        <div v-show="!showDialog">
                            <div v-if="t =getCourse(week, period, day)">
                                {{ t.courseName }}
                                <hr>
                                {{ t.classroomName }}
                                <hr>
                                {{t.profession+ t.classNumber+"班" }}
                            </div>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </section>
    <dialog ref="dialog">
        <form>
            <div>
                <label>周次：</label>
                <input type="text" readonly :value="info.week">
            </div>
            <div>
                <label>星期：</label>
                <input type="text" readonly :value="info.dayofweek">
            </div>
            <div>
                <label>节次：</label>
                <input type="text" readonly :value="info.section">
            </div>
            <div v-show="!showDetail">
                <label>课程：</label>
                <select v-model="tempCourse" @change="changeCourse" required>
                    <option v-for="(item,index) in tempCourses" :key="index" :value="item" >{{ item.name }}</option>
                </select>
            </div>
            <div v-show="!showDetail">
                <label>班级：</label>
                <input type="text" readonly :value="classNumber" required>
            </div>
            <div v-show="showDetail">
                <label>课程：</label>
                <input type="text" readonly :value="tempName">
            </div>
            <div v-show="showDetail">
                <label>班级：</label>
                <input type="text" readonly :value="tempNumber" >
            </div>
            <div>
                <button @click.prevent="addAppointment" v-show="!showDetail">确 定</button>
                <button @click.prevent="deleteAppointment" v-show="showDetail">删 除</button>
                <button @click.prevent="closeDialog">关 闭</button>
            </div>
        </form>
    </dialog>
</template>

<style scoped>
    .course-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }

    .course-table th, .course-table td {
        border: 1px solid rgb(79, 112, 219);
        /* padding: 10px; */
        text-align: center;
    }

    .course-table th {
        background-color: rgb(79, 112, 219);
        color: white;
    }

    .course-cell {
        min-width: 120px;
        height: 80px;
        vertical-align: top;
    }
    td.course-cell:hover{
        box-shadow: 0 0 10px lightskyblue;
    }
    td div{
        width: 100%;
        height: 100%;
    }
    dialog{
        text-align: center;
        padding: 20px;
        margin: auto;
        border-radius: 20px;
    }
    dialog input,select{
        width: calc(100% - 48px);
        border: 2px solid rgb(79, 112, 219);
        border-radius: 10px;
        padding: 10px;
        outline: none;
        margin-bottom: 10px;
    }
    dialog input:hover,select:hover{
        box-shadow: 0 0 10px lightskyblue;
    }
    dialog button{
        background-color: white;
        padding: 5px 20px;
        border-radius: 10px;
        border: 1px solid rgb(79, 112, 219);
        color: rgb(79, 112, 219);
    }
    dialog button:hover{
        background-color: rgb(79, 112, 219);
        color: white;
    }
    .highlightR{
        background-color: aquamarine;
    }
    .highlightE{
        background-color: red;
    }
</style>

<script setup>
    import request from '@/axios/request';
    import { inject, ref } from 'vue';

    
    const user = JSON.parse(localStorage.getItem("user"));
    const token = localStorage.getItem("token");
    const props = defineProps(['week', 'classroom', 'showDialog']);

    let appointmentList = ref([]);

    if(user.role === "QAQ<"){
        request.get("admin/get/appointmentDTO",{headers:{'token': token}}).then(res =>{
            appointmentList.value = res.data.data;
        });
    }
    else{
        request.get("teacher/get/appointmentDTO",{headers:{'token': token}}).then(res =>{
            appointmentList.value = res.data.data;
        });
    }


    // const tempAppointment = {
    //     week:0,
    //     dayofweek:0,
    //     section:0,
    //     course:{id:"", name:""},
    //     teacher:{id:"",name:""},
    //     classroom:{id:"",name:""}
    // };

    const getAppointment = (week, section, dayofweek, room) => {
        // console.log("params:", week, section,dayofweek, room);
        // console.log("app",appointmentList);
        for (let i = 0; i < appointmentList.value.length; i++) {
            let a = appointmentList.value[i];
            if (a.week === week && a.section === section && a.dayofweek === dayofweek && a.classroomName===room) {
                return a;
            }
        }
        return null;
    };


    
    const uid = user.id;
    const getCourse = (week, section, dayofweek) => {
        for (let i = 0; i < appointmentList.value.length; i++) {
            let a = appointmentList.value[i];
            if (a.week === week && a.section === section && a.dayofweek === dayofweek && a.teacherId=== uid) {
                return a;
            }
        }
        return null;
    };

    

    const info = ref({
        week:null,
        dayofweek:null,
        section:null,
        course:null,
        classroom:null,
        teacher: null
    });

    const course = {
        id: "",
        name: ""
    }
    const classRoom = {
        id:"",
        name:""
    }
    const teacher ={
        id:"",
        name:""
    }



    // console.log("********", props.classroom);
    const dialog = ref(null);
    const tempUser = JSON.parse(localStorage.getItem("user"));
    let tempCourses = ref(null); 
    const tempCourse= ref(null);
    const classNumber = ref(null);
    request.get("teacher/get/course", { headers: { 'token': token } }).then(res => {
        tempCourses.value = res.data.data;
        // console.log(tempCourses.value);
    });
    const changeCourse = ()=>{
        course.id= tempCourse.value.id;
        course.name = tempCourse.value.name;
        classNumber.value = tempCourse.value.classNumber;
        info.value.course = course;
    }


    const showDetail = ref(false);
    const openDialog = (day, period)=>{
        if(props.showDialog)
        {
            teacher.id = tempUser.id;
            teacher.name = tempUser.name;
            classRoom.id = props.classroom.id;
            classRoom.name = props.classroom.name;

            info.value.dayofweek=day;
            info.value.section = period;
            info.value.week = props.week;
            info.value.classroom = classRoom;
            info.value.teacher = teacher;
            dialog.value.showModal();   
        }
    }

    const addAppointment = ()=>{
        // console.log("info:", info.value);
        if(!course.id)
        {
            alert("课程不能为空");
            return;
        }
        info.value.classroom = JSON.stringify(info.value.classroom);
        info.value.course = JSON.stringify(info.value.course);
        info.value.teacher = JSON.stringify(info.value.teacher);
        request.post("teacher/add/appointment", info.value, {headers:{'token': token}}).then(res =>{

        });
        closeDialog();
        window.location.reload();
    }
    const closeDialog = ()=>{
        showDetail.value=false;
        dialog.value.close();
    }
    const tempName = ref("");
    const tempNumber = ref("");
    const tempAppointment = ref({});
    const openDetail = (week, section, day, room)=>{
        if(props.showDialog)
        {
            
            let appointment = getAppointment(week, section, day, room);
            if(user.role!="QAQ<"){
                if(uid!==appointment.teacherId) return;
            }
            tempAppointment.value = appointment;
            teacher.id = appointment.teacherId;
            teacher.name = appointment.teacherName;
            classRoom.id = appointment.classroomId;
            classRoom.name = appointment.classroomName;
            course.id = appointment.courseId;
            course.name = appointment.courseName;

            tempName.value = appointment.courseName;
            tempNumber.value = appointment.classNumber;
            info.value.dayofweek=day;
            info.value.section = section;
            info.value.week = props.week;
            info.value.classroom = classRoom;
            info.value.teacher = teacher;
            
            showDetail.value = true;
            dialog.value.showModal();   
        }
    }
    const deleteAppointment = ()=>{
        // console.log("delete",tempAppointment);
        // console.log("teacher/delete/appointment/"+tempAppointment.value.appointmentId);
        if(user.role = "QAQ<"){
            request.delete("admin/delete/appointment/"+tempAppointment.value.appointmentId, {headers:{'token': token}});
        }
        else{
            request.delete("teacher/delete/appointment/"+tempAppointment.value.appointmentId, {headers:{'token': token}});
        }
        closeDialog();
        alert("删除成功");
        window.location.reload();
    }
</script>