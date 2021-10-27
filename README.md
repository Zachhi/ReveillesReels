# Reveilles-Reels

My implementation of a linux shell

Shell has the ability to function almost as much as the linux/ubuntu shell in your OS, which lets a user navigate through the file system and perform a wide variety of tasks using a series of easy to remember and simple commands. A lot of utility is included, such as the ability to parse through one or more pipes, input output redirection, directory processing, background processes, and more. Some of the commands you can use are detailed below

I just created this repository so I could have this project on my main github account

## Demo

https://www.youtube.com/watch?v=sT9SZdZqSJE

### Types of Commands to Test Shell

#### Simple Commands with Arguments

* echo 'dsfdskj'
* ls
* ls -l /sbin
* ls -l -a
* ps -aux
* ls -l ..

#### Input/Output Redirection

* ps aux > a
* grep /init < a
* grep /init < a > b

#### Single and Multiple Piping

* ls -l | grep <pattern>
* ps aux | awk '/init/{print $1}' | sort -r
* ps aux | awk '/init/{print $1}' | sort -r | awk '/ro/' |grep ro
* ps aux | awk '{print $1$11}' | sort -r | grep root
* awk '{print $1$11}'<test.txt | head -10 | head -8 | head -7| sort > output.txt
  
#### Background Processes

* sleep 20 &

#### Directory Processing
* cd ../../
* cd .
* cd /home/
* cd -
 
Type quit to end the program

### Dependencies

* Some way to compile and execute c++ code
* GNU Make (Not required, but makes executing easier)

### Installing and Executing

* Download the source code from github, or clone the repository into Visual Studio
* Type `make` and then execute `./run`
* You will want to do this in a virtual enviroment if you edit the files, otherwise you may compromise your computer's health

## Authors

Zachary Chi - zachchi@tamu.edu
  
Emory Fields - emory.c.fields@tamu.edu
  
Morgan Roberts - morgan.roberts00@tamu.edu
  
Allison Edwards - allisone12@tamu.edu
  
Emma Haeussler - emmahaeussler@tamu.edu
  
## License

This project is licensed under the GNU General Public License v2.0 - see the LICENSE.md file for details
