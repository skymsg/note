use std::io;
use std::cmp::Ordering;
use rand::Rng;
fn main() {
		println!("Guess the number!");
		let secret_number = rand::thread_rng().gen_range(0,20);
		loop {
				println!("Please input your guess!");
				let mut guess = String::new();
				io::stdin()
						.read_line(&mut guess)
						.expect("failed to readline");
				let parsed =  guess.trim().parse(); 
				let guess: u32 = match parsed{
						Ok(num) => num,
						Err(_)=> continue,
				};
				println!("You guessed :{}",guess);
				match guess.cmp(&secret_number){
						Ordering::Less => println!("Too small!"),
						Ordering::Greater => println!("Too big!"),
						Ordering::Equal => {
								println!("Congretulation!");
								break;
						}
				}
		}
		println!("the number is {}",secret_number);
}
