use std::io;
fn gcd(a:i32,b:i32)->i32{
	if b==0 {
		return a;
	}else {
		return gcd(b,a%b);
	}

}

fn main() {
	let mut input_a = String::new();
	let mut input_b = String::new();
	io::stdin().read_line(&mut input_a).expect("failed read line!");
	let a:i32 = input_a.trim().parse().expect("please input a number!");
	io::stdin().read_line(&mut input_b).expect("failed read line!");
	let b:i32 = input_b.trim().parse().expect("please input a number!");
	println!("a={0},b={1},gcd(a,b)={2}",a,b,gcd(a,b));

}
