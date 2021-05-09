# WIF3003 Group Project: Museum During Pandemic

## Introduction

This is the repository for our team (Ever Givem)'s submission for WIF3003 Group Project for Semester 2, 2020/2021 session. The purpose of this assignment is to assess the students’ ability to apply adequate programming skills and use appropriate constructs in the Java language to solve a concurrent problem.

Team Members:
1. **Muhammad Rahiman bin Abdulmanab**
2. **Nur Faidz Hazirah binti Nor'Azman**
3. **Muhammad Luqman bin Sulaiman**
4. **Muhammad Farouq bin Shaharuddin**

<hr>

## Project Description

A museum reopens after months of closure due to the COVID-19 pandemic. As a safety measure,
the museum’s management decided to limit the number of visitors in the museum at one time. The
following rules have been set:

1. The museum opens from 9.00 a.m. to 6.00 p.m. daily.
2. The museum receives not more than 900 visitors per day.
3. Not more than 100 visitors are allowed in the museum at one time.
4. The museum has 2 entrances – South Entrance (SE) and North Entrance (NE); and two exits – East Exit (EE) and West Exit (WE).
5. Each entrance and exit has 4 turnstiles (T1-T4) for visitors to access through. The turnstiles have sensors to detect visitors entering or leaving the museum.
6. Visitors use the museum’s mobile app to purchase tickets. The ticketing system refuses the purchase when the daily limit of visitors (900) has been reached.
7. Tickets will be sold from 8.00 a.m. to 5.00 p.m. daily.
8. A control system that integrates with the ticketing system will be built to control visitors’ access to the museum.

You are required to build a multithreaded program to simulate operations of the control system.
The following are assumptions for your simulation:

1. First request to purchase tickets will be made at 8.00 a.m.
2. Subsequent purchase will be made every 1-4 minutes. Each purchase will be for 1-4 tickets.
3. Every ticket has an ID in the form of T####, where #### are running numbers from 0001 to 9999. There is also a timestamp of purchase on each ticket.
4. Visitors enter the museum based on the timestamps on their tickets, i.e. visitors who purchased
their tickets earlier enter the museum before those purchase their tickets later.
5. Each visitor randomly uses a turnstile at either South Entrance or North Entrance to enter the museum.
6. Each visitor stays in the museum for 50-150 minutes. The duration of stay will be randomly
assigned to the visitor when the visitor is entering the museum.
7. After the duration of stay is over, the visitor randomly uses a turnstile at either East Exit or West Exit to leave the museum.

<hr>

## Tasks

Form a group of 4-5 members (not confined to your tutorial group) to develop the program. Use an online collaboration tool (Github, Trello, etc.) for collaborative development. Assessments will be done in Weeks 9 (Phase 1) and 14 (Phase 2). Each member’s responsibility and contribution must be reported.