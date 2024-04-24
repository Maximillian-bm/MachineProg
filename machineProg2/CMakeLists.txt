cmake_minimum_required(VERSION 3.27)
project(MachineProgOffLine)

set(CMAKE_C_STANDARD 11)

add_executable(MachineProgOffLine main.c)

target_link_libraries(MachineProgOffLine Ws2_32)