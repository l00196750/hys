package com.hys.timetable.domain;

import org.springframework.stereotype.Component;

@Component
public class RandomRoundRobin extends AbstractRoundRobin {

    @Override
    public void round() {
        // // 防止死锁
        // List<Studing> studingList = Lists.newArrayList();
        //
        // while (!context.getStudingQueue().isEmpty()) {
        // Studing studing = context.getStudingQueue().poll();
        //
        // if (isCourseStuded(studing)) {
        // continue;
        // }
        //
        // Collection<Lecture> collection =
        // context.getTeachingMap().get(studing.getCourse().getCourseCode());
        // if (collection.isEmpty()) {
        // logger.debug("not found teaching {}", studing);
        // }
        //
        // Iterator<Lecture> iterator = collection.iterator();
        // while (iterator.hasNext()) {
        // Lecture teaching = iterator.next();
        // studing.setPeriod(teaching.getPeriod());
        // studing.setTeacher(teaching.getTeacher());
        //
        // if (isPeriodConflict(studing.getStudent(), teaching.getPeriod())) {
        // // 时间冲突，需要重新放回
        // studingList.add(studing);
        // continue;
        // }
        //
        // //
        // teaching.getStudents().add(studing.getStudent());
        // context.getStudedMap().put(studing.getStudent().getUserCode(), studing);
        //
        // // 冲突的重新放入队列
        // context.getStudingQueue().addAll(studingList);
        // studingList.clear();
        //
        // if (teaching.getStudentCount() <= teaching.getStudents().size()) {
        // // 移除
        // iterator.remove();
        //
        // // 添加新的教学计划
        // Optional<Lecture> newTeaching = TeachingBuilder.newTeaching(teaching, weekMgr);
        // if (newTeaching.isPresent()) {
        // context.getTeachingMap().put(newTeaching.get().getCourse().getCourseCode(),
        // newTeaching.get());
        // }
        // }
        //
        // // 已找到，退出
        // break;
        // }
        // }
    }

}
